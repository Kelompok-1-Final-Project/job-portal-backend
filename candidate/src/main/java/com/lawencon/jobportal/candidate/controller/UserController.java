package com.lawencon.jobportal.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.user.UserEmailGetResDto;
import com.lawencon.jobportal.candidate.dto.user.UserGetResDto;
import com.lawencon.jobportal.candidate.dto.user.UserInsertReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserRegisterByAdminReqDto;
import com.lawencon.jobportal.candidate.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> registerUser(@RequestBody UserInsertReqDto data){
		final InsertResDto response = userService.registerUser(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/byadmin")
	public ResponseEntity<InsertResDto> registerByAdmin(@RequestBody UserRegisterByAdminReqDto data){
		final InsertResDto response = userService.insertCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<UserGetResDto> getByCandidate(@PathVariable String candidateId){
		final UserGetResDto response = userService.getByCandidate(candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<UserEmailGetResDto> getEmail(@RequestParam("email") String candidateId){
		final UserEmailGetResDto response = userService.getEmail(candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
