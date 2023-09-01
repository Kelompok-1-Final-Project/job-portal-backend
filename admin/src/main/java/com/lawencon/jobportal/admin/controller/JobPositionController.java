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
import com.lawencon.jobportal.admin.dto.jobposition.JobPositionGetResDto;
import com.lawencon.jobportal.admin.dto.jobposition.JobPositionInsertReqDto;
import com.lawencon.jobportal.admin.service.JobPositionService;

@RestController
@RequestMapping("job-position")
public class JobPositionController {
	
	@Autowired
	private JobPositionService jobPositionService;
	
	@GetMapping
	public ResponseEntity<List<JobPositionGetResDto>> getAll() {
		final List<JobPositionGetResDto> data = jobPositionService.getAll();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertJob(@RequestBody JobPositionInsertReqDto data){
		final InsertResDto response = jobPositionService.insertJobPosition(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
