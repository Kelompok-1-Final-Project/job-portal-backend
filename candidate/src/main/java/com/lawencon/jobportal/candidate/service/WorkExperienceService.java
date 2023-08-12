package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.WorkExperienceDao;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceGetResDto;

public class WorkExperienceService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private WorkExperienceDao workExperienceDao;
	
	public List<WorkExperienceGetResDto> getByCandidate(String candidateId) {
		final List<WorkExperienceGetResDto> experienceGetResDtos = new ArrayList<>();

		workExperienceDao.getByCandidate(candidateId).forEach(we -> {
			final WorkExperienceGetResDto experienceGetResDto = new WorkExperienceGetResDto();
			experienceGetResDto.setId(we.getId());
			experienceGetResDto.setPositionName(we.getPositionName());
			experienceGetResDto.setCompanyName(we.getCompanyName());
			experienceGetResDto.setStartDate(we.getStartDate().toString());
			experienceGetResDto.setEndDate(we.getEndDate().toString());
		
			experienceGetResDtos.add(experienceGetResDto);
		});

		return experienceGetResDtos;
	}
}
