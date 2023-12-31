package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.candidate.dao.LevelDao;
import com.lawencon.jobportal.candidate.dao.SkillDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dao.UserSkillDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillInsertReqDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillUpdateReqDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillGetResDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillInsertReqDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Level;
import com.lawencon.jobportal.candidate.model.Skill;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.model.UserSkill;
import com.lawencon.jobportal.candidate.util.GeneratorId;

@Service
public class UserSkillService {

	@Autowired
	private UserSkillDao userSkillDao;

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private LevelDao levelDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RestTemplate restTemplate;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<UserSkillGetResDto> getByCandidate(String candidateId) {
		final List<UserSkillGetResDto> userSkillGetResDtos = new ArrayList<>();

		userSkillDao.getByCandidate(candidateId).forEach(us -> {
			final UserSkillGetResDto userSkillGetResDto = new UserSkillGetResDto();
			userSkillGetResDto.setId(us.getId());
			userSkillGetResDto.setLevelName(us.getLevel().getLevelName());
			userSkillGetResDto.setLevelCode(us.getLevel().getLevelCode());
			userSkillGetResDto.setSkillName(us.getSkill().getSkillName());
			userSkillGetResDto.setSkillCode(us.getSkill().getSkillCode());
			userSkillGetResDtos.add(userSkillGetResDto);
		});

		return userSkillGetResDtos;
	}

	public List<SkillGetResDto> getAllSkill() {
		final List<SkillGetResDto> skillGetResDtos = new ArrayList<>();
		skillDao.getAll(Skill.class).forEach(s -> {
			final SkillGetResDto skillGetResDto = new SkillGetResDto();
			skillGetResDto.setId(s.getId());
			skillGetResDto.setSkillName(s.getSkillName());
			skillGetResDto.setSkillCode(s.getSkillCode());
			skillGetResDtos.add(skillGetResDto);
		});
		return skillGetResDtos;
	}

	public List<LevelGetResDto> getAllLevel() {
		final List<LevelGetResDto> listResult = new ArrayList<>();
		levelDao.getAll(Level.class).forEach(l -> {
			final LevelGetResDto result = new LevelGetResDto();
			result.setId(l.getId());
			result.setLevelCode(l.getLevelCode());
			result.setLevelName(l.getLevelName());
			listResult.add(result);
		});
		return listResult;
	}

	public InsertResDto insertSkill(SkillInsertReqDto data) {
		em().getTransaction().begin();

		final Skill skill = new Skill();
		skill.setSkillCode(data.getSkillCode());
		skill.setSkillName(data.getSkillName());
		final Skill skills = skillDao.save(skill);

		final InsertResDto result = new InsertResDto();
		result.setId(skills.getId());
		result.setMessage("Success add Skill");

		em().getTransaction().commit();
		return result;
	}
	
	public InsertResDto insertSkillSendAdmin(SkillInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			
			final String skillCode = GeneratorId.generateCode();
			final Skill skill = new Skill();
			data.setSkillCode(skillCode);
			skill.setSkillCode(skillCode);
			skill.setSkillName(data.getSkillName());
			final Skill skills = skillDao.save(skill);
			
			final String skillInsertAdminAPI = "http://localhost:8080/cities/candidate-insert";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<SkillInsertReqDto> skillInsert = RequestEntity.post(skillInsertAdminAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(skillInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(skills.getId());
				result.setMessage("Success add Skill");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;
	}

	public UpdateResDto update(SkillUpdateReqDto dto) {
		final UpdateResDto response = new UpdateResDto();
		try {
			em().getTransaction().begin();

			Skill skillResult = new Skill();
			final Skill skillDb = skillDao.getByCode(dto.getSkillCode());
			final Skill skillId = skillDao.getById(Skill.class, skillDb.getId());

			skillId.setSkillName(dto.getSkillName());
			skillResult = skillDao.saveAndFlush(skillId);

			response.setVersion(skillResult.getVersion());
			response.setMessage("Skill updated successfully.");
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		return response;
	}

	public InsertResDto insertUserSkill(UserSkillInsertReqDto data) {
		final InsertResDto result = new InsertResDto();

		try {
			em().getTransaction().begin();

			final UserSkill userSkill = new UserSkill();

			final User user = userDao.getById(User.class, data.getCandidateId());
			userSkill.setCandidate(user);

			final Skill skill = skillDao.getById(Skill.class, data.getSkillId());
			if (skill != null) {
				userSkill.setSkill(skill);
			} else {
				final Skill newSkill = new Skill();
				final String skillCode = GeneratorId.generateCode();
				newSkill.setSkillCode(skillCode);
				newSkill.setSkillName(data.getSkillId());
				final Skill skills = skillDao.save(newSkill);

				userSkill.setSkill(skills);

				final InsertResDto resultSkill = new InsertResDto();
				result.setId(resultSkill.getId());
				result.setMessage("Success add Skill");
			}

			final Level level = levelDao.getById(Level.class, data.getLevelId());
			userSkill.setLevel(level);

			final UserSkill userSkillResult = userSkillDao.save(userSkill);

			result.setId(userSkillResult.getId());
			result.setMessage("Skill Successfully Assigned.");

			em().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}

		return result;
	}

	public boolean deleteUserSkill(String userSkillId) {
		em().getTransaction().begin();

		final Boolean result = userSkillDao.deleteById(UserSkill.class, userSkillId);

		em().getTransaction().commit();
		return result;
	}

	public List<UserSkillGetResDto> getSkillByCandidate(String candidateId) {
		final List<UserSkillGetResDto> listResult = new ArrayList<>();
		final List<UserSkill> listSkill = userSkillDao.getByCandidate(candidateId);
		for (UserSkill s : listSkill) {
			final UserSkillGetResDto result = new UserSkillGetResDto();
			result.setId(s.getId());
			result.setLevelName(s.getLevel().getLevelName());
			result.setSkillName(s.getSkill().getSkillName());
			listResult.add(result);
		}
		return listResult;
	}

	public UpdateResDto updateLevel(UserSkillUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		final UserSkill userSkill = userSkillDao.getById(UserSkill.class, data.getUserSkillId());

		final Level level = levelDao.getByCode(data.getLevelCode());
		userSkill.setLevel(level);

		return updateResDto;
	}
}
