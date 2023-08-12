package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.jobportal.candidate.dao.EducationDao;
import com.lawencon.jobportal.candidate.dto.education.EducationGetResDto;

public class EducationService {
	@Autowired
	private EducationDao educationDao;
	
	public List<EducationGetResDto> getByCandidate(String candidateId) {
		final List<EducationGetResDto> educationGetResDtos = new ArrayList<>();

		educationDao.getByCandidate(candidateId).forEach(e -> {
			final EducationGetResDto educationGetResDto = new EducationGetResDto();
			educationGetResDto.setId(e.getId());
			educationGetResDto.setEducationName(e.getEducationName());
			educationGetResDto.setStartDate(e.getStartDate().toString());
			educationGetResDto.setEndDate(e.getEndDate().toString());
			educationGetResDtos.add(educationGetResDto);
		});

		return educationGetResDtos;
	}
}
