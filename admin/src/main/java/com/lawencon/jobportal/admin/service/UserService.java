package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.constant.RoleEnum;
import com.lawencon.jobportal.admin.dao.GenderDao;
import com.lawencon.jobportal.admin.dao.ProfileDao;
import com.lawencon.jobportal.admin.dao.RoleDao;
import com.lawencon.jobportal.admin.dao.UserDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.user.UserGetResDto;
import com.lawencon.jobportal.admin.dto.user.UserInsertReqDto;
import com.lawencon.jobportal.admin.dto.user.UserLoginReqDto;
import com.lawencon.jobportal.admin.dto.user.UserLoginResDto;
import com.lawencon.jobportal.admin.dto.user.UserUpdateIsActiveReqDto;
import com.lawencon.jobportal.admin.model.Gender;
import com.lawencon.jobportal.admin.model.Profile;
import com.lawencon.jobportal.admin.model.Role;
import com.lawencon.jobportal.admin.model.User;
import com.lawencon.jobportal.admin.util.GeneratorId;

@Service
public class UserService implements UserDetailsService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private UserDao userDao;

	@Autowired
	private GenderDao genderDao;

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UserGetResDto> getAll() {
		final List<UserGetResDto> usersDto = new ArrayList<>();
		userDao.getAll(User.class).forEach(u -> {
			final UserGetResDto userGetResDto = new UserGetResDto();
			userGetResDto.setUserId(u.getId());
			userGetResDto.setUserEmail(u.getEmail());
			userGetResDto.setRoleName(u.getRole().getRoleName());
			userGetResDto.setFullName(u.getProfile().getFullName());
			userGetResDto.setUserPhone(u.getProfile().getMobileNumber());
			userGetResDto.setIsActive(u.getIsActive());
			usersDto.add(userGetResDto);
		});

		return usersDto;
	}

	public InsertResDto insert(UserInsertReqDto data) {
		em().getTransaction().begin();

		User userResult = null;
		final InsertResDto insertResDto = new InsertResDto();

		final Profile profile = new Profile();

		profile.setFullName(data.getFullName());
		profile.setMobileNumber(data.getUserPhone());

		final Gender gender = genderDao.getByCode(data.getGenderCode());
		profile.setGender(gender);

		final Profile profileResult = profileDao.save(profile);

		final User user = new User();
		user.setEmail(data.getUserEmail());

		final String pass = GeneratorId.generateCode();

		final String passEncode = passwordEncoder.encode(pass);
		user.setPass(passEncode);
		user.setProfile(profileResult);

		final Role role = roleDao.getByCode(data.getRoleCode());
		user.setRole(role);

		userResult = userDao.save(user);
		
		emailService.sendEmailNewUser("Registered Account", userResult, pass);

		if (userResult != null) {
			insertResDto.setId(userResult.getId());
			insertResDto.setMessage("Insert Data Berhasil");
		}

		em().getTransaction().commit();
		return insertResDto;
	}

	public UserLoginResDto login(UserLoginReqDto userLoginReqDto) {
		final User user = userDao.getByEmail(userLoginReqDto.getUserEmail());

		final UserLoginResDto userLoginResDto = new UserLoginResDto();

		userLoginResDto.setUserId(user.getId());
		userLoginResDto.setRoleCode(user.getRole().getRoleCode());
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
	
	public List<UserGetResDto> getAllHr() {
		final List<UserGetResDto> usersDto = new ArrayList<>();
		
		userDao.getByRoleCode(RoleEnum.HR.roleCode).forEach(u -> {
			final UserGetResDto userGetResDto = new UserGetResDto();
			userGetResDto.setUserId(u.getId());
			userGetResDto.setUserEmail(u.getEmail());
			userGetResDto.setRoleName(u.getRole().getRoleName());
			userGetResDto.setFullName(u.getProfile().getFullName());
			userGetResDto.setUserPhone(u.getProfile().getMobileNumber());
			userGetResDto.setIsActive(u.getIsActive());
			usersDto.add(userGetResDto);
		});

		return usersDto;
	}
	
	public List<UserGetResDto> getAllInterviewer() {
		final List<UserGetResDto> usersDto = new ArrayList<>();
		
		userDao.getByRoleCode(RoleEnum.INTERVIEWER.roleCode).forEach(u -> {
			final UserGetResDto userGetResDto = new UserGetResDto();
			userGetResDto.setUserId(u.getId());
			userGetResDto.setUserEmail(u.getEmail());
			userGetResDto.setRoleName(u.getRole().getRoleName());
			userGetResDto.setFullName(u.getProfile().getFullName());
			userGetResDto.setUserPhone(u.getProfile().getMobileNumber());
			userGetResDto.setIsActive(u.getIsActive());
			usersDto.add(userGetResDto);
		});

		return usersDto;
	}
	
	public UpdateResDto updateUser(UserUpdateIsActiveReqDto data)  {
		em().getTransaction().begin();
		
		final User userDb = userDao.getById(User.class, data.getUserId());
		userDb.setIsActive(data.getIsActive());
		
		final User userResult = userDao.save(userDb);
		
		final UpdateResDto updateResDto = new UpdateResDto();
		
		updateResDto.setVersion(userResult.getVersion());
		updateResDto.setMessage("Update Berhasil!!!");
		
		em().getTransaction().commit();

		return updateResDto;
	}
}
