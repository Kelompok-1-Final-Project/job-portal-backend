package com.lawencon.jobportal.candidate.service;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.constant.PersonTypeEnum;
import com.lawencon.jobportal.candidate.dao.FileDao;
import com.lawencon.jobportal.candidate.dao.GenderDao;
import com.lawencon.jobportal.candidate.dao.MaritalStatusDao;
import com.lawencon.jobportal.candidate.dao.PersonTypeDao;
import com.lawencon.jobportal.candidate.dao.ProfileDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.user.UserInsertReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserLoginReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserLoginResDto;
import com.lawencon.jobportal.candidate.dto.user.UserRegisterByAdminReqDto;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Gender;
import com.lawencon.jobportal.candidate.model.MaritalStatus;
import com.lawencon.jobportal.candidate.model.PersonType;
import com.lawencon.jobportal.candidate.model.Profile;
import com.lawencon.jobportal.candidate.model.User;
import com.lawencon.jobportal.candidate.util.DateConvert;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProfileDao profileDao;

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
	private PasswordEncoder passwordEncoder;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public UserLoginResDto login(UserLoginReqDto userLoginReqDto) {
		final User user = userDao.getByEmail(userLoginReqDto.getUserEmail());

		final UserLoginResDto userLoginResDto = new UserLoginResDto();

		userLoginResDto.setUserId(user.getId());
		userLoginResDto.setUserName(user.getProfile().getFullName());
//		userLoginResDto.setPhotoId(user.getProfile());

		return userLoginResDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userDao.getByEmail(username);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(username, user.getPass(), new ArrayList<>());
		}
		throw new UsernameNotFoundException("Email tidak ditemukan");
	}

	public InsertResDto registerUser(UserInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Profile profile = new Profile();
			profile.setFullName(data.getFullname());
			final PersonType personType = personTypeDao.getByCode(PersonTypeEnum.CANDIDATE.typeCode);
			profile.setPersonType(personType);
			final Profile profiles = profileDao.saveNoLogin(profile, () -> "SYSTEM");

			final User candidate = new User();

			candidate.setEmail(data.getEmail());
			final String password = passwordEncoder.encode(data.getPass());
			candidate.setPass(password);
			candidate.setProfile(profiles);
			final User candidates = userDao.saveNoLogin(candidate, () -> "SYSTEM");

			final String candidateRegisterAdminAPI = "http://localhost:8080/candidates/self";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			final RequestEntity<UserInsertReqDto> candidateRegister = RequestEntity.post(candidateRegisterAdminAPI)
					.headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(candidateRegister,
					InsertResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(candidates.getId());
				result.setMessage("Register Successfully.");
				em().getTransaction().commit();

			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Register Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}
	
	public InsertResDto insertCandidate(UserRegisterByAdminReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Profile candidateProfile = new Profile();
			candidateProfile.setFullName(data.getFullName());
			candidateProfile.setIdNumber(data.getIdNumber());
			candidateProfile.setSummary(data.getSummary());
			candidateProfile.setBirthdate(DateConvert.convertDate(data.getBirthdate()).toLocalDate());
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
			final Profile profileResult = profileDao.save(candidateProfile);

			final User candidate = new User();
			candidate.setEmail(data.getEmail());
			candidate.setProfile(profileResult);
			candidate.setPass(data.getPassword());
			
			final User candidateResult = userDao.save(candidate);

			result.setId(candidateResult.getId());
			result.setMessage("Candidate inserted successfully");

			em().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		return result;
	}

}
