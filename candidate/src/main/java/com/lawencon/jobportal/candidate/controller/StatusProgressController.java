package com.lawencon.jobportal.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.jobstatus.CandidateProgressInsertReqDto;
import com.lawencon.jobportal.candidate.service.ProgressStatusService;


@RestController
@RequestMapping("status-progress")
public class StatusProgressController {

	@Autowired
	private ProgressStatusService progressStatusService;
	
	@PostMapping("/candidate")
	public ResponseEntity<InsertResDto> insertCandidateProgress(@RequestBody CandidateProgressInsertReqDto data){
		final InsertResDto response = progressStatusService.insertProgressStatusCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
//	@PostMapping("/application")
//	public ResponseEntity<InsertResDto> insertApplication(@RequestBody ApplicationInsertReqDto data){
//		final InsertResDto response = progressStatusService.insertApplication(data);
//		return new ResponseEntity<>(response, HttpStatus.CREATED);
//	}
	
}	
