package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetProfileResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateGetResDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateInsertReqDto;
import com.lawencon.jobportal.admin.dto.candidate.CandidateSelfRegisterReqDto;
import com.lawencon.jobportal.admin.dto.candidate.UpdateCvReqDto;
import com.lawencon.jobportal.admin.dto.candidate.UpdateProfileReqDto;
import com.lawencon.jobportal.admin.dto.candidate.UpdateSummaryReqDto;
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
	
	@PatchMapping("/cvUpdate")
	public ResponseEntity<UpdateResDto> updateCV(@RequestBody UpdateCvReqDto data){
		final UpdateResDto response = candidateService.updateCv(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/summaryUpdate")
	public ResponseEntity<UpdateResDto> updateSummary(@RequestBody UpdateSummaryReqDto data){
		final UpdateResDto response = candidateService.updateSummary(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/profileUpdate")
	public ResponseEntity<UpdateResDto> updateProfile(@RequestBody UpdateProfileReqDto data){
		final UpdateResDto response = candidateService.updateCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{candidateId}")
	public ResponseEntity<CandidateGetProfileResDto> getByCandidate(@PathVariable String candidateId){
		final CandidateGetProfileResDto response = candidateService.getByCandidate(candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
