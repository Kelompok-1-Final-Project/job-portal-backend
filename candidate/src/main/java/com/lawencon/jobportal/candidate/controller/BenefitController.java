package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitInsertReqDto;
import com.lawencon.jobportal.candidate.dto.benefit.BenefitUpdateReqDto;
import com.lawencon.jobportal.candidate.dto.benefit.JobBenefitGetResDto;
import com.lawencon.jobportal.candidate.service.BenefitService;

@RestController
@RequestMapping("benefits")
public class BenefitController {
	
	@Autowired
	private BenefitService benefitService;
	
	@GetMapping
	public ResponseEntity<List<JobBenefitGetResDto>> getByJob(@RequestParam String jobId){
		final List<JobBenefitGetResDto> data = benefitService.getByJob(jobId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody BenefitInsertReqDto data) {
		final InsertResDto response = benefitService.insert(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody BenefitUpdateReqDto data) {
		final UpdateResDto response = benefitService.update(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
