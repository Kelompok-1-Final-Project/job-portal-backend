package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetResDto;
import com.lawencon.jobportal.admin.model.Candidate;

@Service
public class CandidateService {
	
	@Autowired
	private CandidateDao candidateDao;
	
	public List<CandidateGetResDto> getAll(){
		final List<CandidateGetResDto> candidateGetResDtos = new ArrayList<>();
		
		candidateDao.getAll(Candidate.class).forEach(c -> {
			final CandidateGetResDto candidateGetResDto = new CandidateGetResDto();
			candidateGetResDto.setId(c.getId());
			candidateGetResDto.setEmail(c.getEmail());
			candidateGetResDto.setFullName(c.getCandidateProfile().getFullName());
			candidateGetResDto.setMobileNumber(c.getCandidateProfile().getMobileNumber());
			candidateGetResDto.setTypeName(c.getCandidateProfile().getPersonType().getTypeName());
			candidateGetResDto.setGenderName(c.getCandidateProfile().getGender().getGenderName());
			candidateGetResDto.setVer(c.getVersion());
			candidateGetResDtos.add(candidateGetResDto);
		});

		return candidateGetResDtos;
	}
	
	public CandidateGetResDto getByName(String name){
		final Candidate candidate = candidateDao.getByName(name);
		
		final CandidateGetResDto candidateGetResDto = new CandidateGetResDto();
		candidateGetResDto.setId(candidate.getId());
		candidateGetResDto.setEmail(candidate.getEmail());
		candidateGetResDto.setFullName(candidate.getCandidateProfile().getFullName());
		candidateGetResDto.setMobileNumber(candidate.getCandidateProfile().getMobileNumber());
		candidateGetResDto.setTypeName(candidate.getCandidateProfile().getPersonType().getTypeName());
		candidateGetResDto.setGenderName(candidate.getCandidateProfile().getGender().getGenderName());
		candidateGetResDto.setVer(candidate.getVersion());
		
		return candidateGetResDto;
	}
}
