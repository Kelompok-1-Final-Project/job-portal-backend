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
import com.lawencon.jobportal.admin.dto.workexperience.WorkExperienceGetResDto;
import com.lawencon.jobportal.admin.dto.workexperience.WorkExperienceInsertReqDto;
import com.lawencon.jobportal.admin.service.WorkExperienceService;

@RestController
@RequestMapping("work-experience")
public class WorkExperienceController {
	@Autowired
	private WorkExperienceService workExperienceService;
	
	@GetMapping
	public ResponseEntity<List<WorkExperienceGetResDto>> getWorkExperienceByCandidate(@RequestParam String candidateId){
		final List<WorkExperienceGetResDto> response = workExperienceService.getByCandidate(candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertexperience (@RequestBody WorkExperienceInsertReqDto data){
		final InsertResDto response = workExperienceService.insertWorkExperience(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
