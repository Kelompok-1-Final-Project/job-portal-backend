package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.LevelDao;
import com.lawencon.jobportal.candidate.dao.SkillDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dao.UserSkillDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillInsertReqDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillGetResDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillInsertReqDto;
import com.lawencon.jobportal.candidate.model.Level;
import com.lawencon.jobportal.candidate.model.Skill;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.model.UserSkill;

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
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<UserSkillGetResDto> getByCandidate(String candidateId) {
		final List<UserSkillGetResDto> userSkillGetResDtos = new ArrayList<>();

		userSkillDao.getByCandidate(candidateId).forEach(us -> {
			final UserSkillGetResDto userSkillGetResDto = new UserSkillGetResDto();
			userSkillGetResDto.setId(us.getId());
			userSkillGetResDto.setLevelName(us.getLevel().getLevelName());
			userSkillGetResDto.setSkillName(us.getSkill().getSkillName());
			userSkillGetResDtos.add(userSkillGetResDto);
		});

		return userSkillGetResDtos;
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
	
	public InsertResDto insertUserSkill(UserSkillInsertReqDto data) {
		em().getTransaction().begin();
		
		final UserSkill userSkill = new UserSkill();
		final User user = userDao.getById(User.class, data.getCandidateId());
		userSkill.setCandidate(user);
		final Skill skill = skillDao.getById(Skill.class, data.getSkillId());
		userSkill.setSkill(skill);
		final Level level = levelDao.getById(Level.class, data.getLevelId());
		userSkill.setLevel(level);
		final UserSkill userSkillResult = userSkillDao.save(userSkill);
		final InsertResDto result = new InsertResDto();
		result.setId(userSkillResult.getId());
		result.setMessage("Skill Successfully Assigned.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public boolean deleteUserSkill(String userSkillId) {
		em().getTransaction().begin();
		
		final Boolean result = userSkillDao.deleteById(UserSkill.class, userSkillId);
		
		em().getTransaction().commit();
		return result;
	}
	
	public List<UserSkillGetResDto> getSkillByCandidate(String candidateId){
		final List<UserSkillGetResDto> listResult = new ArrayList<>();
		final List<UserSkill> listSkill = userSkillDao.getByCandidate(candidateId);
		for(UserSkill s:listSkill) {
			final UserSkillGetResDto result = new UserSkillGetResDto();
			result.setId(s.getId());
			result.setLevelName(s.getLevel().getLevelName());
			result.setSkillName(s.getSkill().getSkillName());
			listResult.add(result);
		}
		return listResult;
	}
}
