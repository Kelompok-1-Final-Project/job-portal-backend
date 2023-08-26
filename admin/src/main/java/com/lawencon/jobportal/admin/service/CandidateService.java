package com.lawencon.jobportal.admin.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.admin.constant.PersonTypeEnum;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.CandidateProfileDao;
import com.lawencon.jobportal.admin.dao.FileDao;
import com.lawencon.jobportal.admin.dao.GenderDao;
import com.lawencon.jobportal.admin.dao.MaritalStatusDao;
import com.lawencon.jobportal.admin.dao.PersonTypeDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetProfileResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateInsertReqDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateSelfRegisterReqDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateUpdateIsActiveReqDto;
import com.lawencon.jobportal.admin.dto.candidate.UpdateCvReqDto;
import com.lawencon.jobportal.admin.dto.candidate.UpdateProfileReqDto;
import com.lawencon.jobportal.admin.dto.candidate.UpdateSummaryReqDto;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Gender;
import com.lawencon.jobportal.admin.model.MaritalStatus;
import com.lawencon.jobportal.admin.model.PersonType;
import com.lawencon.jobportal.admin.util.DateConvert;
import com.lawencon.jobportal.admin.util.GeneratorId;

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
	private RestTemplate restTemplate;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<CandidateGetResDto> getAll() {
		final List<CandidateGetResDto> candidateGetResDtos = new ArrayList<>();

		candidateDao.getAll(Candidate.class).forEach(c -> {
			final CandidateGetResDto candidateGetResDto = new CandidateGetResDto();
			candidateGetResDto.setId(c.getId());
			candidateGetResDto.setEmail(c.getEmail());
			candidateGetResDto.setFullName(c.getCandidateProfile().getFullName());
			candidateGetResDto.setMobileNumber(c.getCandidateProfile().getMobileNumber());
			candidateGetResDto.setTypeName(c.getCandidateProfile().getPersonType().getTypeName());
			if(c.getCandidateProfile().getGender()!=null) {
				candidateGetResDto.setGenderName(c.getCandidateProfile().getGender().getGenderName());				
			}
			candidateGetResDto.setVer(c.getVersion());
			candidateGetResDtos.add(candidateGetResDto);
		});

		return candidateGetResDtos;
	}

	public CandidateGetResDto getByName(String name) {
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
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final CandidateProfile candidateProfile = new CandidateProfile();
			candidateProfile.setFullName(data.getFullName());
			candidateProfile.setIdNumber(data.getIdNumber());
			candidateProfile.setSummary(data.getSummary());
			candidateProfile.setBirthDate(DateConvert.convertDate(data.getBirthdate()).toLocalDate());
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

			final String pass = GeneratorId.generateCode();
			final String message = "Email: " + data.getEmail() + "\nPassword: " + pass;

			emailService.sendEmail(data.getEmail(), "Registrasi User", message);

			final String password = passwordEncoder.encode(pass);
			data.setPassword(password);
			final Candidate candidateResult = candidateDao.save(candidate);

			final String candidateInsertAPI = "http://localhost:8081/users/byadmin";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CandidateInsertReqDto> candidateInsert = RequestEntity.post(candidateInsertAPI)
					.headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(candidateInsert,
					InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(candidateResult.getId());
				result.setMessage("Candidate inserted successfully");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return result;
	}

	public InsertResDto selfRegister(CandidateSelfRegisterReqDto data) {

		em().getTransaction().begin();

		final CandidateProfile profile = new CandidateProfile();
		profile.setFullName(data.getFullname());
		final PersonType personType = personTypeDao.getByCode(PersonTypeEnum.CANDIDATE.typeCode);
		profile.setPersonType(personType);
		final CandidateProfile profiles = candidateProfileDao.saveNoLogin(profile, () -> "SYSTEM");

		final Candidate candidate = new Candidate();
		candidate.setEmail(data.getEmail());
		candidate.setCandidateProfile(profiles);
		final Candidate candidates = candidateDao.saveNoLogin(candidate, () -> "SYSTEM");

		final InsertResDto result = new InsertResDto();
		result.setId(candidates.getId());
		result.setMessage("Register Successfully.");
		em().getTransaction().commit();

		return result;
	}

	public UpdateResDto updateCv(UpdateCvReqDto data) {
		final UpdateResDto result = new UpdateResDto();

		try {
			em().getTransaction().begin();
			final Candidate candidateDb = candidateDao.getByEmail(data.getCandidateEmail());
			final Candidate candidate = candidateDao.getById(Candidate.class, candidateDb.getId());
			final CandidateProfile profile = candidateProfileDao.getById(CandidateProfile.class,
					candidate.getCandidateProfile().getId());
			final String oldFileId = profile.getCv().getId();

			final File file = new File();
			file.setFile(data.getFile());
			file.setExt(data.getExt());
			final File newFile = fileDao.save(file);

			profile.setCv(newFile);
			final CandidateProfile newProfile = candidateProfileDao.saveAndFlush(profile);

			if(oldFileId != null) {
				fileDao.deleteById(File.class, oldFileId);				
			}

			result.setVersion(newProfile.getVersion());
			result.setMessage("CV updated successfully.");
			em().getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}

		return result;
	}

	public UpdateResDto updateSummary(UpdateSummaryReqDto data) {
		final UpdateResDto result = new UpdateResDto();

		try {
			em().getTransaction().begin();
			final Candidate candidateDb = candidateDao.getByEmail(data.getCandidateEmail());
			final Candidate candidate = candidateDao.getById(Candidate.class, candidateDb.getId());
			final CandidateProfile profile = candidateProfileDao.getById(CandidateProfile.class,
					candidate.getCandidateProfile().getId());

			profile.setSummary(data.getSummary());
			final CandidateProfile newProfile = candidateProfileDao.saveAndFlush(profile);

			result.setVersion(newProfile.getVersion());
			result.setMessage("Summary updated successfully.");
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public UpdateResDto updateCandidate(UpdateProfileReqDto data) {
		final UpdateResDto result = new UpdateResDto();
		try {
			em().getTransaction().begin();

			final Candidate candidateDb = candidateDao.getByEmail(data.getEmail());
			final Candidate candidate = candidateDao.getById(Candidate.class, candidateDb.getId());

			final CandidateProfile candidateProfile = candidateProfileDao.getById(CandidateProfile.class,
			candidate.getCandidateProfile().getId());
			candidateProfile.setFullName(data.getFullName());
			candidateProfile.setIdNumber(data.getIdNumber());
			candidateProfile.setBirthDate(DateConvert.convertDate(data.getBirthdate()).toLocalDate());
			candidateProfile.setExpectedSalary(data.getExpectedSalary());
			candidateProfile.setMobileNumber(data.getMobileNumber());

			if (data.getPhotoFiles() != null) {
				final String oldPhotoId = candidateProfile.getPhoto().getId();

				final File photo = new File();
				photo.setExt(data.getPhotoExt());
				photo.setFile(data.getPhotoFiles());
				final File photoResult = fileDao.save(photo);
				candidateProfile.setPhoto(photoResult);
				fileDao.deleteById(File.class, oldPhotoId);
			}

			candidateProfile.setExpectedSalary(Integer.valueOf(data.getExpectedSalary()));

			final MaritalStatus maritalStatus = maritalStatusDao.getByCode(data.getMaritalStatusCode());
			final MaritalStatus maritalResult = maritalStatusDao.getById(MaritalStatus.class, maritalStatus.getId());
			candidateProfile.setMaritalStatus(maritalResult);
			
			final Gender gender = genderDao.getByCode(data.getGenderCode());
			candidateProfile.setGender(gender);

			final CandidateProfile profileResult = candidateProfileDao.save(candidateProfile);

			result.setVersion(profileResult.getVersion());
			result.setMessage("Candidate updated successfully");
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}
	
	public CandidateGetProfileResDto getByCandidate(String candidateId) {
		final Candidate user = candidateDao.getById(Candidate.class, candidateId);
		
		final CandidateGetProfileResDto userGetResDto = new CandidateGetProfileResDto();
		userGetResDto.setFullName(user.getCandidateProfile().getFullName());
		if(user.getCandidateProfile().getIdNumber() != null) {
			userGetResDto.setIdNumber(user.getCandidateProfile().getIdNumber());
		}
		
		userGetResDto.setEmail(user.getEmail());
		
		if(user.getCandidateProfile().getMobileNumber() != null) {
			userGetResDto.setPhone(user.getCandidateProfile().getMobileNumber());
		}
		
		if(user.getCandidateProfile().getMaritalStatus() != null) {
			userGetResDto.setMaritalStatusCode(user.getCandidateProfile().getMaritalStatus().getStatusCode());
			userGetResDto.setMaritalStatus(user.getCandidateProfile().getMaritalStatus().getStatusName());
		}
		
		if(user.getCandidateProfile().getGender() != null) {
			userGetResDto.setGender(user.getCandidateProfile().getGender().getGenderName());
		}
		
		if(user.getCandidateProfile().getExpectedSalary() != null) {
			userGetResDto.setExpectedSalary(user.getCandidateProfile().getExpectedSalary());
		}
		
		if(user.getCandidateProfile().getBirthDate() != null) {
			final LocalDate curDate = LocalDate.now();  
			final LocalDate getBirth = user.getCandidateProfile().getBirthDate();
			final int age = Period.between(getBirth, curDate).getYears();
			final String ageText = age + " years old";
			userGetResDto.setAge(ageText);
		}
		
		if(user.getCandidateProfile().getCv() != null) {
			userGetResDto.setCvId(user.getCandidateProfile().getCv().getId());
		}
		
		if(user.getCandidateProfile().getPhoto() != null) {
			userGetResDto.setPhotoId(user.getCandidateProfile().getPhoto().getId());
		}
		
		if(user.getCandidateProfile().getSummary() != null) {			
			userGetResDto.setSummary(user.getCandidateProfile().getSummary());
		}
		
		return userGetResDto;
		
	}
	
	public UpdateResDto updateIsActive(CandidateUpdateIsActiveReqDto data)  {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			final Candidate userDb = candidateDao.getById(Candidate.class, data.getUserId());
			data.setEmail(userDb.getEmail());
			userDb.setIsActive(data.getIsActive());
			
			final Candidate userResult = candidateDao.save(userDb);

			final String candidateUpdateAdminAPI = "http://localhost:8081/users/update-acvite";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CandidateUpdateIsActiveReqDto> candidateUpdate = RequestEntity.patch(candidateUpdateAdminAPI)
					.headers(headers).body(data);

			final ResponseEntity<UpdateResDto> responseAdmin = restTemplate.exchange(candidateUpdate,
					UpdateResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				updateResDto.setVersion(userResult.getVersion());
				updateResDto.setMessage("Update Berhasil!!!");
				em().getTransaction().commit();

			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Is Active Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}
}
