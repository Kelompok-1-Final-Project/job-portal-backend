package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.DeleteResDto;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.education.EducationGetResDto;
import com.lawencon.jobportal.candidate.dto.education.EducationInsertReqDto;
import com.lawencon.jobportal.candidate.service.EducationService;

@RestController
@RequestMapping("educations")
public class EducationController {

	@Autowired
	private EducationService educationService;
	
	@GetMapping
	public ResponseEntity<List<EducationGetResDto>> getEducationByCandidate(@RequestParam String candidateId){
		final List<EducationGetResDto> data = educationService.getByCandidate(candidateId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertEducation(@RequestBody EducationInsertReqDto data){
		final InsertResDto response = educationService.insertEducation(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteEducation(@RequestParam String educationId){
		final Boolean result = educationService.deleteEducation(educationId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Education Successfully Deleted.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Education Failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
