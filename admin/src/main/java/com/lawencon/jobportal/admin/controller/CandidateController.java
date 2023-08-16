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
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateInsertReqDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateSelfRegisterReqDto;
import com.lawencon.jobportal.admin.service.CandidateService;

@RestController
@RequestMapping("candidates")
public class CandidateController {
	
	@Autowired
	private CandidateService candidateService;
	
	@GetMapping
	public ResponseEntity<List<CandidateGetResDto>> getAll() {
		final List<CandidateGetResDto> data = candidateService.getAll();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/filter/name")
	public ResponseEntity<CandidateGetResDto> getByName(
			@RequestParam("n") String name) {
		final CandidateGetResDto data = candidateService.getByName(name);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertCandidate(@RequestBody CandidateInsertReqDto data){
		final InsertResDto response = candidateService.insertCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/self")
	public ResponseEntity<InsertResDto> selfRegister(@RequestBody CandidateSelfRegisterReqDto data){
		final InsertResDto response = candidateService.selfRegister(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
