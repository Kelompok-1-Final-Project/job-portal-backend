package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.user.UserLoginReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserLoginResDto;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;
	
	public UserLoginResDto login(UserLoginReqDto userLoginReqDto)  {
		final User user = userDao.getByEmail(userLoginReqDto.getUserEmail());

		final UserLoginResDto userLoginResDto = new UserLoginResDto();
		
		userLoginResDto.setUserId(user.getId());
		userLoginResDto.setUserName(user.getProfile().getFullName());
//		userLoginResDto.setPhotoId(user.getProfile());
		
		
		return userLoginResDto;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user =  userDao.getByEmail(username);
		if(user != null) {
			return new org.springframework.security.core.userdetails.User(username, user.getPass(), new ArrayList<>());
		}
		throw new UsernameNotFoundException("Email tidak ditemukan");
	}
}
