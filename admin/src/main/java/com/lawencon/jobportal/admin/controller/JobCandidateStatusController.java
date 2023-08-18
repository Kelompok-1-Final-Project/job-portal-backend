package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.jobcandidatestatus.JobCandidateStatusGetResDto;
import com.lawencon.jobportal.admin.service.JobCandidateStatusService;

@RestController
@RequestMapping("jobcandidatestatus")
public class JobCandidateStatusController {
	
	@Autowired
	private JobCandidateStatusService jobCandidateStatusService;
	
	@GetMapping
	public ResponseEntity<List<JobCandidateStatusGetResDto>> getAllIndustry(@RequestParam String jobCode) {
		final List<JobCandidateStatusGetResDto>data = jobCandidateStatusService.getByJob(jobCode);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
