package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.candidate.dao.FileDao;
import com.lawencon.jobportal.candidate.dao.GenderDao;
import com.lawencon.jobportal.candidate.dao.MaritalStatusDao;
import com.lawencon.jobportal.candidate.dao.PersonTypeDao;
import com.lawencon.jobportal.candidate.dao.ProfileDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.profile.GenderGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.MaritalGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.PersonTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.ProfileGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.UpdateCvReqDto;
import com.lawencon.jobportal.candidate.dto.profile.UpdateCvSendAdminReqDto;
import com.lawencon.jobportal.candidate.dto.profile.UpdateSummaryReqDto;
import com.lawencon.jobportal.candidate.dto.profile.UpdateSummarySendAdminReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserUpdateReqDto;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Gender;
import com.lawencon.jobportal.candidate.model.MaritalStatus;
import com.lawencon.jobportal.candidate.model.PersonType;
import com.lawencon.jobportal.candidate.model.Profile;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.util.DateConvert;

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
	private UserDao userDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private RestTemplate restTemplate;

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

	public List<GenderGetResDto> getAllGender() {
		final List<GenderGetResDto> genderGetResDtos = new ArrayList<>();
		genderDao.getAll(Gender.class).forEach(g -> {
			final GenderGetResDto genderGetResDto = new GenderGetResDto();
			genderGetResDto.setId(g.getId());
			genderGetResDto.setGenderName(g.getGenderName());
			genderGetResDto.setGenderCode(g.getGenderCode());
			genderGetResDtos.add(genderGetResDto);
		});
		return genderGetResDtos;
	}

	public UpdateResDto updateCandidate(UserUpdateReqDto data) {
		final UpdateResDto result = new UpdateResDto();
		try {
			em().getTransaction().begin();

			final User candidate = userDao.getById(User.class, data.getCandidateId());
			System.out.println(data.getCandidateId());
			data.setEmail(candidate.getEmail());
			
			final Profile candidateProfile = profileDao.getById(Profile.class, candidate.getProfile().getId());
			candidateProfile.setFullName(data.getFullName());
			candidateProfile.setIdNumber(data.getIdNumber());
			candidateProfile.setBirthdate(DateConvert.convertDate(data.getBirthdate()).toLocalDate());
			candidateProfile.setExpectedSalary(data.getExpectedSalary());
			candidateProfile.setMobileNumber(data.getMobileNumber());

			if(data.getPhotoFiles() != null) {
				String oldPhotoId = "";
				if(candidateProfile.getPhoto() != null) {
					oldPhotoId=candidateProfile.getPhoto().getId();
				}
				
				final File photo = new File();
				photo.setExt(data.getPhotoExt());
				photo.setFile(data.getPhotoFiles());
				final File photoResult = fileDao.save(photo);
				candidateProfile.setPhoto(photoResult);
				if(oldPhotoId!=null&&oldPhotoId!="") {
					fileDao.deleteById(File.class, oldPhotoId);
				}
			}
			
			candidateProfile.setExpectedSalary(Integer.valueOf(data.getExpectedSalary()));

			final MaritalStatus maritalStatus = maritalStatusDao.getByCode(data.getMaritalStatusCode());
			final MaritalStatus maritalResult = maritalStatusDao.getById(MaritalStatus.class, maritalStatus.getId());
			candidateProfile.setMaritalStatus(maritalResult);
			
			final Gender gender = genderDao.getByCode(data.getGenderCode());
			candidateProfile.setGender(gender);

			final Profile profileResult = profileDao.save(candidateProfile);

			final String candidateUpdateAdminAPI = "http://localhost:8080/candidates/profileUpdate";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<UserUpdateReqDto> candidateRegister = RequestEntity.patch(candidateUpdateAdminAPI)
					.headers(headers).body(data);

			final ResponseEntity<UpdateResDto> responseAdmin = restTemplate.exchange(candidateRegister,
					UpdateResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				result.setVersion(profileResult.getVersion());
				result.setMessage("Candidate updated successfully");
				em().getTransaction().commit();

			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Profile Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
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

	public UpdateResDto updateCv(UpdateCvReqDto data) {
		final UpdateResDto result = new UpdateResDto();

		try {
			em().getTransaction().begin();
			final User candidate = userDao.getById(User.class, data.getCandidateId());
			final Profile profile = profileDao.getById(Profile.class, candidate.getProfile().getId());
			final String oldFileId = profile.getCv().getId();

			final File file = new File();
			file.setFile(data.getFile());
			file.setExt(data.getExt());
			final File newFile = fileDao.save(file);

			profile.setCv(newFile);
			final Profile newProfile = profileDao.saveAndFlush(profile);

			if(oldFileId != null) {
				fileDao.deleteById(File.class, oldFileId);				
			}

			final UpdateCvSendAdminReqDto sendData = new UpdateCvSendAdminReqDto();
			sendData.setCandidateEmail(candidate.getEmail());
			sendData.setFile(data.getFile());
			sendData.setExt(data.getExt());

			final String cvUpdateAdminAPI = "http://localhost:8080/candidates/cvUpdate";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<UpdateCvSendAdminReqDto> cvUpdate = RequestEntity.patch(cvUpdateAdminAPI)
					.headers(headers).body(sendData);

			final ResponseEntity<UpdateResDto> responseAdmin = restTemplate.exchange(cvUpdate, UpdateResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				result.setVersion(newProfile.getVersion());
				result.setMessage("CV updated successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public UpdateResDto updateSummary(UpdateSummaryReqDto data) {
		final UpdateResDto result = new UpdateResDto();

		try {
			em().getTransaction().begin();
			final User candidate = userDao.getById(User.class, data.getCandidateId());
			final Profile profile = profileDao.getById(Profile.class, candidate.getProfile().getId());

			profile.setSummary(data.getSummary());
			final Profile newProfile = profileDao.saveAndFlush(profile);

			final UpdateSummarySendAdminReqDto sendData = new UpdateSummarySendAdminReqDto();
			sendData.setCandidateEmail(candidate.getEmail());
			sendData.setSummary(data.getSummary());

			final String summaryUpdateAdminAPI = "http://localhost:8080/candidates/summaryUpdate";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<UpdateSummarySendAdminReqDto> summaryUpdate = RequestEntity.patch(summaryUpdateAdminAPI)
					.headers(headers).body(sendData);

			final ResponseEntity<UpdateResDto> responseAdmin = restTemplate.exchange(summaryUpdate, UpdateResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				result.setVersion(newProfile.getVersion());
				result.setMessage("Summary updated successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}
}
