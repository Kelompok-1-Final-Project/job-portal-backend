package com.lawencon.jobportal.candidate.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.answer.AnswerInsertReqDto;
import com.lawencon.jobportal.candidate.dto.answer.TestGetResDto;
import com.lawencon.jobportal.candidate.service.AnswerService;

@RestController
@RequestMapping("answers")
public class AnswerController {
	
	@Autowired
	private AnswerService answerService;
	
	@GetMapping("/{jobId}")
	public ResponseEntity<TestGetResDto> getAllQuestion(@PathVariable String jobId){
		final TestGetResDto data = answerService.getAllQuestion(jobId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody AnswerInsertReqDto data) {
		final InsertResDto response = answerService.answerInsert(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
