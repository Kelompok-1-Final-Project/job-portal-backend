package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.candidate.dao.LevelDao;
import com.lawencon.jobportal.candidate.dao.SkillDao;
import com.lawencon.jobportal.candidate.dao.UserSkillDao;
import com.lawencon.jobportal.candidate.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillGetResDto;
import com.lawencon.jobportal.candidate.model.Skill;
import com.lawencon.jobportal.candidate.model.Level;


@Service
public class UserSkillService {
	@Autowired
	private UserSkillDao userSkillDao;
	
	@Autowired
	private SkillDao skillDao;
	
	@Autowired
	private LevelDao levelDao;
	
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
}
