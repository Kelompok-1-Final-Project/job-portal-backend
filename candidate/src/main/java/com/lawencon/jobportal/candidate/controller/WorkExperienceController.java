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
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceGetResDto;
import com.lawencon.jobportal.candidate.dto.workexperience.WorkExperienceInsertReqDto;
import com.lawencon.jobportal.candidate.service.WorkExperienceService;

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
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteExperience(@RequestParam String experienceId){
		final Boolean result = workExperienceService.deleteExperience(experienceId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Experience Successfully Deleted.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Experience Failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
