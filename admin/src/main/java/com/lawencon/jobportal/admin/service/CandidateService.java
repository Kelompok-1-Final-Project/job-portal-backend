package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.CandidateProfileDao;
import com.lawencon.jobportal.admin.dao.PersonTypeDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateInsertReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.PersonType;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

@Service
public class CandidateService {
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private CandidateProfileDao candidateProfileDao;
	
	@Autowired
	private PersonTypeDao personTypeDao;
	
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
	
	public InsertResDto insertCandidate(CandidateInsertReqDto data) {
		final CandidateProfile candidateProfile = new CandidateProfile();
		candidateProfile.setFullName(data.getFullName());
		final PersonType personTypeResult = personTypeDao.getByCode(data.getPersonTypeCode());
		final PersonType personType = personTypeDao.getById(PersonType.class, personTypeResult.getId());
		candidateProfile.setPersonType(personType);
		final CandidateProfile profileResult = candidateProfileDao.save(candidateProfile);
		final Candidate candidate = new Candidate();
		candidate.setEmail(data.getEmail());
		candidate.setCandidateProfile(profileResult);
		final String pass = generateCode();
		candidate.setPassword(pass);
		final Candidate candidateResult = candidateDao.save(candidate);
		final InsertResDto result = new InsertResDto();
		result.setId(candidateResult.getId());
		result.setMessage("Insert Candidate Successfully.");
		return result;
	}
}
