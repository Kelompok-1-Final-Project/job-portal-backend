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
import com.lawencon.jobportal.candidate.dto.savejob.SaveJobGetResDto;
import com.lawencon.jobportal.candidate.dto.savejob.SaveJobInsertReqDto;
import com.lawencon.jobportal.candidate.service.SaveJobService;

@RestController
@RequestMapping("save-jobs")
public class SaveJobController {

	@Autowired
	private SaveJobService saveJobService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertSaveJob(@RequestBody SaveJobInsertReqDto data){
		final InsertResDto response = saveJobService.insertSaveJob(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<SaveJobGetResDto>> getSaveJobByCandidate(@RequestParam String candidateId){
		final List<SaveJobGetResDto> response = saveJobService.getByCandidate(candidateId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteExperience(@RequestParam String saveJobId){
		final Boolean result = saveJobService.deleteSaveJob(saveJobId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Save Job Deleted");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Save Job Failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
