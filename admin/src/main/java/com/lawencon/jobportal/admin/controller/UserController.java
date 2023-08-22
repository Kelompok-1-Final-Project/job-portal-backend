package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.user.UserGetResDto;
import com.lawencon.jobportal.admin.dto.user.UserInsertReqDto;
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
	
	@GetMapping("/hr")
	public ResponseEntity<List<UserGetResDto>> getAllHr(@RequestParam("sp") Integer startPosition, @RequestParam("ep")Integer endPosition) {
		final List<UserGetResDto> data = userService.getAllHr(startPosition, endPosition);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/interviewer")
	public ResponseEntity<List<UserGetResDto>> getAllInterviewer(@RequestParam("sp") Integer startPosition, @RequestParam("ep")Integer endPosition) {
		final List<UserGetResDto> data = userService.getAllInterviewer(startPosition, endPosition);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(
			@RequestBody UserInsertReqDto data) {
		final InsertResDto response = userService.insert(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}