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

import com.lawencon.jobportal.candidate.dto.DeleteResDto;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionGetResDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionInsertReqDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionOptionUpdateReqDto;
import com.lawencon.jobportal.candidate.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportal.candidate.service.QuestionService;

@RestController
@RequestMapping("questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertQuestion(@RequestBody List<QuestionInsertReqDto> data){
		final InsertResDto response = questionService.insertQuestion(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<DeleteResDto> deleteQuestion(@RequestParam String questionId){
		final Boolean result = questionService.deleteQuestion(questionId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Delete Question Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Question Failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<QuestionGetResDto>> getAllQuestion(){
		final List<QuestionGetResDto> response = questionService.getAllQuestion();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/by-skill-test")
	public ResponseEntity<List<QuestionGetResDto>> getQuestionBySkillTest(@RequestParam String skillTestId){
		final List<QuestionGetResDto> response = questionService.getAllQuestionBySkillTest(skillTestId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateQuestion(@RequestBody QuestionUpdateReqDto data){
		final UpdateResDto response = questionService.updateQuestion(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/option")
	public ResponseEntity<UpdateResDto> updateQuestionOption(@RequestBody QuestionOptionUpdateReqDto data){
		final UpdateResDto response = questionService.updateQuestionOption(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
