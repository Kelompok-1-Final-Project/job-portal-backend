package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.jobportal.candidate.dao.UserSkillDao;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillGetResDto;

public class UserSkillService {
	@Autowired
	private UserSkillDao userSkillDao;
	
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
}
