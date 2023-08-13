package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.candidate.dao.GenderDao;
import com.lawencon.jobportal.candidate.dao.MaritalStatusDao;
import com.lawencon.jobportal.candidate.dao.PersonTypeDao;
import com.lawencon.jobportal.candidate.dto.profile.GenderGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.MaritalGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.PersonTypeGetResDto;
import com.lawencon.jobportal.candidate.model.Gender;
import com.lawencon.jobportal.candidate.model.MaritalStatus;
import com.lawencon.jobportal.candidate.model.PersonType;

@Service
public class ProfileService {

	@Autowired
	private PersonTypeDao personTypeDao;

	@Autowired
	private MaritalStatusDao maritalStatusDao;
	
	@Autowired
	private GenderDao genderDao;

	public List<MaritalGetResDto> getAllMaritalStatus() {
		final List<MaritalGetResDto> maritalGetResDtos = new ArrayList<>();
		maritalStatusDao.getAll(MaritalStatus.class).forEach(m -> {
			final MaritalGetResDto maritalGetResDto = new MaritalGetResDto();
			maritalGetResDto.setId(m.getId());
			maritalGetResDto.setMaritalStatusName(m.getStatusName());
			maritalGetResDto.setMaritalStatusCode(m.getStatusCode());
			maritalGetResDtos.add(maritalGetResDto);
		});
		return maritalGetResDtos;
	}

	public List<PersonTypeGetResDto> getAllPersonType() {
		final List<PersonTypeGetResDto> personTypeGetResDtos = new ArrayList<>();
		personTypeDao.getAll(PersonType.class).forEach(m -> {
			final PersonTypeGetResDto personTypeGetResDto = new PersonTypeGetResDto();
			personTypeGetResDto.setTypeId(m.getId());
			personTypeGetResDto.setTypeName(m.getTypeName());
			personTypeGetResDto.setTypeCode(m.getTypeCode());
			personTypeGetResDtos.add(personTypeGetResDto);
		});
		return personTypeGetResDtos;
	}
	public List<GenderGetResDto> getAllGender(){
		final List<GenderGetResDto> genderGetResDtos= new ArrayList<>();
		genderDao.getAll(Gender.class).forEach(g -> {
			final GenderGetResDto genderGetResDto = new GenderGetResDto();
			genderGetResDto.setId(g.getId());
			genderGetResDto.setGenderName(g.getGenderName());
			genderGetResDto.setGenderCode(g.getGenderCode());
			genderGetResDtos.add(genderGetResDto);
		});
		return genderGetResDtos;
	}
	
}
