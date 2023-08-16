package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.blacklist.BlacklistGetResDto;
import com.lawencon.jobportal.admin.dto.blacklist.BlacklistInsertReqDto;
import com.lawencon.jobportal.admin.service.BlacklistService;

@RestController
@RequestMapping("blacklist")
public class BlacklistController {
	
	@Autowired
	private BlacklistService blacklistService;
	
	@GetMapping
	public ResponseEntity<List<BlacklistGetResDto>>getAll(){
		final List<BlacklistGetResDto> response = blacklistService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertBlacklist(@RequestBody BlacklistInsertReqDto data){
		final InsertResDto response = blacklistService.insertBlacklist(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
