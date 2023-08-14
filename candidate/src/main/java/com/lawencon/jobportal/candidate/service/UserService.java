package com.lawencon.jobportal.candidate.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.ProfileDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.user.UserInsertReqDto;
import com.lawencon.jobportal.candidate.model.Profile;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public InsertResDto registerUser(UserInsertReqDto data) {
		em().getTransaction().begin();
		
		final Profile profile = new Profile();
		profile.setFullName(data.getFullname());
		final Profile profiles = profileDao.save(profile);
		
		final User candidate = new User();
		candidate.setEmail(data.getEmail());
		candidate.setPass(data.getPass());
		candidate.setProfile(profiles);
		final User candidates = userDao.save(candidate);
		
		final InsertResDto result = new InsertResDto();
		result.setId(candidates.getId());
		result.setMessage("Register Successfully.");
		em().getTransaction().commit();
		return result;
	}
	
	
}
