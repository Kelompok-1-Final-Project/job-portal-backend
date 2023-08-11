package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.CandidateProfileDao;
import com.lawencon.jobportal.admin.dao.FileDao;
import com.lawencon.jobportal.admin.dao.GenderDao;
import com.lawencon.jobportal.admin.dao.MaritalStatusDao;
import com.lawencon.jobportal.admin.dao.PersonTypeDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateInsertReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Gender;
import com.lawencon.jobportal.admin.model.MaritalStatus;
import com.lawencon.jobportal.admin.model.PersonType;

@Service
public class CandidateService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private CandidateProfileDao candidateProfileDao;
	
	@Autowired
	private PersonTypeDao personTypeDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private GenderDao genderDao;
	
	@Autowired
	private MaritalStatusDao maritalStatusDao;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		em().getTransaction().begin();
		
		final CandidateProfile candidateProfile = new CandidateProfile();
		candidateProfile.setFullName(data.getFullName());
		candidateProfile.setIdNumber(data.getIdNumber());
		candidateProfile.setSummary(data.getSummary());
		candidateProfile.setBirthDate(LocalDate.parse(data.getBirthdate()));
		candidateProfile.setMobileNumber(data.getMobileNumber());
		
		final File photo = new File();
		photo.setExt(data.getPhotoExt());
		photo.setFile(data.getPhotoFiles());
		final File photoResult = fileDao.save(photo);
		candidateProfile.setPhoto(photoResult);
		
		final File cv = new File();
		cv.setExt(data.getCvExt());
		cv.setFile(data.getCvFiles());
		final File cvResult = fileDao.save(cv);
		candidateProfile.setCv(cvResult);
		
		candidateProfile.setExpectedSalary(Integer.valueOf(data.getExpectedSalary()));
		
		final Gender gender = genderDao.getByCode(data.getGenderCode());
		final Gender genderResult = genderDao.getById(Gender.class, gender.getId());
		candidateProfile.setGender(genderResult);
		
		final MaritalStatus maritalStatus = maritalStatusDao.getByCode(data.getMaritalStatusCode());
		final MaritalStatus maritalResult = maritalStatusDao.getById(MaritalStatus.class, maritalStatus.getId());
		candidateProfile.setMaritalStatus(maritalResult);
		
		final PersonType personTypeResult = personTypeDao.getByCode(data.getPersonTypeCode());
		final PersonType personType = personTypeDao.getById(PersonType.class, personTypeResult.getId());
		candidateProfile.setPersonType(personType);
		final CandidateProfile profileResult = candidateProfileDao.save(candidateProfile);
		
		final Candidate candidate = new Candidate();
		candidate.setEmail(data.getEmail());
		candidate.setCandidateProfile(profileResult);
		
		final String pass = generateCode();
		mailService.sendMail("Password Candidate", "Your Password "+ pass, data.getEmail());
		final String password = passwordEncoder.encode(pass);
		candidate.setPassword(password);
		final Candidate candidateResult = candidateDao.save(candidate);
		
		final InsertResDto result = new InsertResDto();
		result.setId(candidateResult.getId());
		result.setMessage("Candidate inserted successfully");
		
		em().getTransaction().commit();
		return result;
	}
}
