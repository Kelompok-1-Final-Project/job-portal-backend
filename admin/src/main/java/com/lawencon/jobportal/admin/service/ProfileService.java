package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.GenderDao;
import com.lawencon.jobportal.admin.dao.MaritalStatusDao;
import com.lawencon.jobportal.admin.dao.RoleDao;
import com.lawencon.jobportal.admin.dto.profile.GenderGetResDto;
import com.lawencon.jobportal.admin.dto.profile.MaritalGetResDto;
import com.lawencon.jobportal.admin.dto.profile.RoleGetResDto;
import com.lawencon.jobportal.admin.model.Gender;
import com.lawencon.jobportal.admin.model.MaritalStatus;
import com.lawencon.jobportal.admin.model.Role;

@Service
public class ProfileService {
	
	@Autowired
	MaritalStatusDao maritalStatusDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	GenderDao genderDao;
	
	public List<MaritalGetResDto> getAllMaritalStatus(){
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
	
	public List<RoleGetResDto> getAllRoleStatus(){
		final List<RoleGetResDto> roleGetResDtos = new ArrayList<>();
		roleDao.getAll(Role.class).forEach(m -> {
			final RoleGetResDto roleGetResDto = new RoleGetResDto();
			roleGetResDto.setId(m.getId());
			roleGetResDto.setRoleName(m.getRoleName());
			roleGetResDto.setRoleCode(m.getRoleCode());
			roleGetResDtos.add(roleGetResDto);
		});
		return roleGetResDtos;
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
