package com.lawencon.jobportal.candidate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.FileDao;
import com.lawencon.jobportal.candidate.dao.GenderDao;
import com.lawencon.jobportal.candidate.dao.MaritalStatusDao;
import com.lawencon.jobportal.candidate.dao.PersonTypeDao;
import com.lawencon.jobportal.candidate.dao.ProfileDao;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.profile.GenderGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.MaritalGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.PersonTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.ProfileGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.ProfileUpdateReqDto;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Gender;
import com.lawencon.jobportal.candidate.model.MaritalStatus;
import com.lawencon.jobportal.candidate.model.PersonType;
import com.lawencon.jobportal.candidate.model.Profile;

@Service
public class ProfileService {

	@Autowired
	private PersonTypeDao personTypeDao;

	@Autowired
	private MaritalStatusDao maritalStatusDao;
	
	@Autowired
	private GenderDao genderDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private FileDao fileDao;
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}

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
	
	public UpdateResDto updateProfile(ProfileUpdateReqDto data) {
		em().getTransaction().begin();
		
		final Profile profile = profileDao.getById(Profile.class, data.getProfileId());
		profile.setFullName(data.getFullName());
		profile.setIdNumber(data.getIdNumber());
		profile.setFullName(data.getFullName());
		profile.setSummary(data.getSummary());
		profile.setBirthdate(LocalDate.parse(data.getBirthdate()));
		profile.setMobileNumber(data.getMobileNumber());
		final File file = new File();
		file.setExt(data.getPhotoExt());
		file.setFile(data.getPhotoFiles());
		final File photo = fileDao.save(file);
		profile.setPhoto(photo);
		file.setExt(data.getCvExt());
		file.setFile(data.getCvFiles());
		final File cv = fileDao.save(file);
		profile.setCv(cv);
		profile.setExpectedSalary(Integer.valueOf(data.getExpectedSalary()));
		final Gender gender = genderDao.getById(Gender.class, data.getGenderId());
		profile.setGender(gender);
		final MaritalStatus status = maritalStatusDao.getById(MaritalStatus.class, data.getMaritalStatusId());
		profile.setMaritalStatus(status);
		final PersonType type = personTypeDao.getById(PersonType.class, data.getPersonTypeId());
		profile.setPersonType(type);
		final Profile profiles = profileDao.saveAndFlush(profile);
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(profiles.getVersion());
		result.setMessage("Profile Successfully Updated.");

		em().getTransaction().commit();
		return result;
		
	}
	
	public ProfileGetResDto getProfileByCandidate(String userId) {
		final Profile profile = profileDao.getByUser(userId);
		final ProfileGetResDto result = new ProfileGetResDto();
		result.setId(profile.getId());
		result.setIdNumber(profile.getIdNumber());
		result.setFullName(profile.getFullName());
		result.setSummary(profile.getSummary());
		result.setBirthdate(profile.getBirthdate().toString());
		result.setMobileNumber(profile.getMobileNumber());
		result.setPhotoId(profile.getPhoto().getId());
		result.setCvId(profile.getCv().getId());
		result.setExpectedSalary(profile.getExpectedSalary());
		result.setGenderName(profile.getGender().getGenderName());
		result.setStatusName(profile.getMaritalStatus().getStatusName());
		result.setTypeName(profile.getPersonType().getTypeName());
		return result;
	}
}
