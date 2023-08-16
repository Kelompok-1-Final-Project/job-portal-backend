package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

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
import com.lawencon.jobportal.admin.dao.LevelDao;
import com.lawencon.jobportal.admin.dao.SkillDao;
import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.admin.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.admin.dto.skill.SkillInsertReqDto;
import com.lawencon.jobportal.admin.dto.skill.SkillUpdateReqDto;
import com.lawencon.jobportal.admin.model.Level;
import com.lawencon.jobportal.admin.model.Skill;

@Service
public class SkillService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private SkillDao skillDao;
	
	@Autowired
	private LevelDao levelDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public InsertResDto insert(SkillInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		
		try {
			em().getTransaction().begin();
			
			final String skillCode = generateCode();
			final Skill skill = new Skill();
			data.setSkillCode(skillCode);
			skill.setSkillCode(skillCode);
			skill.setSkillName(data.getSkillName());
			final Skill skills = skillDao.save(skill);

			final String skillInserCandidateAPI = "http://localhost:8081/skills";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<SkillInsertReqDto> skillInsert = RequestEntity.post(skillInserCandidateAPI).headers(headers)
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
	
	public List<SkillGetResDto> getAllSkill(){
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
	
	public SkillGetResDto getByName(String name){
			final SkillGetResDto industryGetResDto = new SkillGetResDto();
			final Skill skill = skillDao.getByName(name);
			industryGetResDto.setId(skill.getId());
			industryGetResDto.setSkillName(skill.getSkillName());
			industryGetResDto.setSkillCode(skill.getSkillCode());
			return industryGetResDto;
	}
	
	
	public UpdateResDto update(SkillUpdateReqDto dto) {
		em().getTransaction().begin();
		
		Skill skillResult = new Skill();
		final Skill skillDb = skillDao.getByCode(dto.getSkillCode());
		final Skill skillUpdate = skillDao.getById(Skill.class, skillDb.getId());
		
		skillUpdate.setSkillName(dto.getSkillName());
		skillResult = skillDao.saveAndFlush(skillUpdate);

		final UpdateResDto response = new UpdateResDto();
		response.setVersion(skillResult.getVersion());
		response.setMessage("Berhasil Update skill");
		
		em().getTransaction().commit();
		return response;
	}
	
	
	public DeleteResDto deleteByCode(String code) {
		em().getTransaction().begin();
		
		boolean checkUpdate;
		final Skill skillDb = skillDao.getByCode(code);
		final Skill skillDelete = skillDao.getById(Skill.class, skillDb.getId());
		final DeleteResDto response = new DeleteResDto();
		
		checkUpdate=skillDao.deleteById(Skill.class, skillDelete);
		
		if(checkUpdate) {
			response.setMessage("Successful delete data");			
		}else {
			response.setMessage("Failed to delete data");
		}
		
		em().getTransaction().commit();
		return response;
	}
	
	public List<LevelGetResDto> getAllLevel(){
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
}
