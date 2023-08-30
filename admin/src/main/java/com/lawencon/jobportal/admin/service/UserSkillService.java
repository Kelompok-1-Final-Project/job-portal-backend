package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.LevelDao;
import com.lawencon.jobportal.admin.dao.SkillDao;
import com.lawencon.jobportal.admin.dao.UserSkillDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.skill.SkillUpdateReqDto;
import com.lawencon.jobportal.admin.dto.userskill.UserSkillGetResDto;
import com.lawencon.jobportal.admin.dto.userskill.UserSkillInsertReqDto;
import com.lawencon.jobportal.admin.dto.userskill.UserSkillUpdateReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Level;
import com.lawencon.jobportal.admin.model.Skill;
import com.lawencon.jobportal.admin.model.UserSkill;
import com.lawencon.jobportal.admin.util.GeneratorId;

@Service
public class UserSkillService {

	@Autowired
	private UserSkillDao userSkillDao;

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private LevelDao levelDao;

	@Autowired
	private CandidateDao candidateDao;

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

			final Candidate user = candidateDao.getById(Candidate.class, data.getCandidateId());
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
