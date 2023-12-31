package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.profile.GenderGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.MaritalGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.PersonTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.profile.UpdateCvReqDto;
import com.lawencon.jobportal.candidate.dto.profile.UpdateSummaryReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserUpdateReqDto;
import com.lawencon.jobportal.candidate.service.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/marital")
	public ResponseEntity<List<MaritalGetResDto>> getMarital() {
		final List<MaritalGetResDto> response = profileService.getAllMaritalStatus();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/gender")
	public ResponseEntity<List<GenderGetResDto>> getGender() {
		final List<GenderGetResDto> response = profileService.getAllGender();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/person-type")
	public ResponseEntity<List<PersonTypeGetResDto>> getPersonType() {
		final List<PersonTypeGetResDto> response = profileService.getAllPersonType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateProfile(@RequestBody UserUpdateReqDto data){
		final UpdateResDto response = profileService.updateCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/cvUpdate")
	public ResponseEntity<UpdateResDto> updateCV(@RequestBody UpdateCvReqDto data){
		final UpdateResDto response = profileService.updateCv(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/summaryUpdate")
	public ResponseEntity<UpdateResDto> updateCV(@RequestBody UpdateSummaryReqDto data){
		final UpdateResDto response = profileService.updateSummary(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
