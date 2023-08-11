package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.profile.GenderGetResDto;
import com.lawencon.jobportal.admin.dto.profile.MaritalGetResDto;
import com.lawencon.jobportal.admin.dto.profile.RoleGetResDto;
import com.lawencon.jobportal.admin.service.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("marital")
	public ResponseEntity<List<MaritalGetResDto>> getMarital() {
		final List<MaritalGetResDto> response = profileService.getAllMaritalStatus();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("gender")
	public ResponseEntity<List<GenderGetResDto>> getGender() {
		final List<GenderGetResDto> response = profileService.getAllGender();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("role")
	public ResponseEntity<List<RoleGetResDto>> getRole() {
		final List<RoleGetResDto> response = profileService.getAllRoleStatus();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
