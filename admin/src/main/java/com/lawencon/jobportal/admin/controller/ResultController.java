package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.result.ResultGetResDto;
import com.lawencon.jobportal.admin.dto.result.ResultInsertReqDto;
import com.lawencon.jobportal.admin.service.ResultService;

@RestController
@RequestMapping("results")
public class ResultController {
	
	@Autowired
	private ResultService resultService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertAnswer(@RequestBody ResultInsertReqDto data){
		final InsertResDto response = resultService.insertResult(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteResult(@RequestParam String resultId){
		final Boolean result = resultService.deleteResult(resultId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Delete Result Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Result failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/job/{jobId}")
	public ResponseEntity<List<ResultGetResDto>> getByJob(@PathVariable String jobId){
		final List<ResultGetResDto> response = resultService.getAllByJob(jobId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
