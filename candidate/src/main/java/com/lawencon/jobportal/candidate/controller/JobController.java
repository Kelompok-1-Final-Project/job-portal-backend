package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.candidate.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.candidate.service.JobService;

@RestController
@RequestMapping("jobs")
public class JobController {

	@Autowired
	private JobService jobService;
	
	@GetMapping("job-status")
	public ResponseEntity<List<JobStatusGetResDto>> getAllJobStatus() {
		final List<JobStatusGetResDto> data = jobService.getAllJobStatus();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("employment-type")
	public ResponseEntity<List<EmploymentTypeGetResDto>> getAllEmploymentType(){
		final List<EmploymentTypeGetResDto> response = jobService.getAllEmploymentType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertJob(@RequestBody JobInsertReqDto data){
		final InsertResDto response = jobService.insertJob(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
