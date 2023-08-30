package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.user.UserGetResDto;
import com.lawencon.jobportal.admin.dto.user.UserInsertReqDto;
import com.lawencon.jobportal.admin.dto.user.UserUpdateIsActiveReqDto;
import com.lawencon.jobportal.admin.dto.user.UserUpdateReqDto;
import com.lawencon.jobportal.admin.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserGetResDto>> getAll() {
		final List<UserGetResDto> data = userService.getAll();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
//	@GetMapping
//	public ResponseEntity<UserGetResDto> getById(String userId) {
//		final UserGetResDto data = userService.getById(userId);
//		return new ResponseEntity<>(data, HttpStatus.OK);
//	}
	
	@GetMapping("/hr")
	public ResponseEntity<List<UserGetResDto>> getAllHr() {
		final List<UserGetResDto> data = userService.getAllHr();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/interviewer")
	public ResponseEntity<List<UserGetResDto>> getAllInterviewer() {
		final List<UserGetResDto> data = userService.getAllInterviewer();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(
			@RequestBody UserInsertReqDto data) {
		final InsertResDto response = userService.insert(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping()
	public ResponseEntity<UpdateResDto> updateUser(
			@RequestBody UserUpdateReqDto data){
		final UpdateResDto response = userService.updateUser(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/update-active")
	public ResponseEntity<UpdateResDto> updateIsActive(
			@RequestBody UserUpdateIsActiveReqDto data){
		final UpdateResDto response = userService.updateIsActive(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}