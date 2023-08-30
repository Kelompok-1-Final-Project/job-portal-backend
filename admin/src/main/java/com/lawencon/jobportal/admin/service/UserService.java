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
import com.lawencon.jobportal.admin.dao.FileDao;
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
import com.lawencon.jobportal.admin.dto.user.UserUpdateReqDto;
import com.lawencon.jobportal.admin.model.File;
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
	private FileDao fileDao;

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
			userGetResDto.setFileId(u.getProfile().getFile().getId());
			userGetResDto.setIsActive(u.getIsActive());
			usersDto.add(userGetResDto);
		});

		return usersDto;
	}
	
	public UserGetResDto getById(String userId) {
		final User user = userDao.getById(User.class, userId);
		
		final UserGetResDto userGetResDto = new UserGetResDto();
		userGetResDto.setUserId(user.getId());
		userGetResDto.setUserEmail(user.getEmail());
		userGetResDto.setRoleName(user.getRole().getRoleName());
		userGetResDto.setFullName(user.getProfile().getFullName());
		userGetResDto.setUserPhone(user.getProfile().getMobileNumber());
		userGetResDto.setFileId(user.getProfile().getFile().getId());
		userGetResDto.setIsActive(user.getIsActive());
		
		return userGetResDto;
	}

	public InsertResDto insert(UserInsertReqDto data) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();

			User userResult = null;

			final Profile profile = new Profile();
			profile.setFullName(data.getFullName());
			
			if(data.getUserPhone() != null) {
				profile.setMobileNumber(data.getUserPhone());			
			}

			if(data.getGenderCode() != null) {
				final Gender gender = genderDao.getByCode(data.getGenderCode());
				profile.setGender(gender);			
			}
			
			if(data.getFile() != null) {
				final File file = new File();
				file.setFile(data.getFile());
				file.setExt(data.getExt());
				fileDao.save(file);
			}

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
			
		}catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return insertResDto;
	}

	public UserLoginResDto login(UserLoginReqDto userLoginReqDto) {
		final User user = userDao.getByEmail(userLoginReqDto.getUserEmail());

		final UserLoginResDto userLoginResDto = new UserLoginResDto();

		userLoginResDto.setUserId(user.getId());
		userLoginResDto.setRoleCode(user.getRole().getRoleCode());
		userLoginResDto.setUserName(user.getProfile().getFullName());
		userLoginResDto.setPhotoId(user.getProfile().getFile().getId());

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
			userGetResDto.setFileId(u.getProfile().getFile().getId());
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
			userGetResDto.setFileId(u.getProfile().getFile().getId());
			userGetResDto.setIsActive(u.getIsActive());
			usersDto.add(userGetResDto);
		});

		return usersDto;
	}
	
	public UpdateResDto updateUser(UserUpdateReqDto data)  {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();

			final User userDb = userDao.getById(User.class, data.getUserId());
			final Role role = roleDao.getById(Role.class, data.getRoleId());
			userDb.setRole(role);
			userDb.setEmail(data.getEmail());
			
			final Profile profileDb = profileDao.getById(Profile.class, userDb.getProfile().getId());
			profileDb.setFullName(data.getFullName());
			
			if(data.getRoleId() != null) {
				profileDb.setMobileNumber(data.getMobileNumber());			
			}
			
			if(data.getGenderId() != null) {
				final Gender gender = genderDao.getById(Gender.class, data.getGenderId());
				profileDb.setGender(gender);			
			}
			
			if(data.getFile() != null) {
				final String oldFileId = userDb.getProfile().getFile().getId();
				
				final File file = new File();
				file.setFile(data.getFile());
				file.setExt(data.getExt());
				
				if(oldFileId != null) {
					fileDao.deleteById(File.class, oldFileId);
				}
			}
				
			final Profile profileResult = profileDao.save(profileDb);
			userDb.setProfile(profileResult);
			final User userResult = userDao.save(userDb);
			
			updateResDto.setVersion(userResult.getVersion());
			updateResDto.setMessage("Update Berhasil!!!");
			
			em().getTransaction().commit();

			
		}catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}
	
	public UpdateResDto updateIsActive(UserUpdateIsActiveReqDto data)  {
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
