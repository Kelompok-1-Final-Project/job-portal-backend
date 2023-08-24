package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestGetResDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestInsertReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestQuestionInsertReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.SkillTestUpdateReqDto;
import com.lawencon.jobportal.admin.dto.skilltest.TestGetResDto;
import com.lawencon.jobportal.admin.service.JobService;
import com.lawencon.jobportal.admin.service.SkillTestService;

@RestController
@RequestMapping("skilltests")
public class SkillTestController {

	@Autowired
	private SkillTestService skillTestService;

	@Autowired
	private JobService jobService;

	@GetMapping
	public ResponseEntity<List<SkillTestGetResDto>> getAll() {
		final List<SkillTestGetResDto> data = skillTestService.getAll();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("filter/{candidateId}/{jobId}")
	public ResponseEntity<SkillTestGetResDto> getByFilter(@PathVariable String candidateId,
			@PathVariable String jobId) {
		final SkillTestGetResDto data = skillTestService.getByCandidateAndJob(candidateId, jobId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/{jobId}")
	public ResponseEntity<TestGetResDto> getAllQuestion(@PathVariable String jobId) {
		final TestGetResDto data = skillTestService.getAllQuestion(jobId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<UpdateResDto> updateScore(@RequestBody SkillTestUpdateReqDto data) {
		final UpdateResDto response = skillTestService.update(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertSkillTest(@RequestBody SkillTestInsertReqDto data) {
		final InsertResDto response = skillTestService.insertSkillTest(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/question")
	public ResponseEntity<InsertResDto> assignTest(@RequestBody SkillTestQuestionInsertReqDto data) {
		final InsertResDto response = skillTestService.assignTest(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/{st}/{q}")
	public ResponseEntity<DeleteResDto> deleteSkillTestQuestion(@PathVariable("st") String skillTestCode,
			@PathVariable("q") String questionCode) {
		final DeleteResDto result = jobService.deleteSkillTestQuestion(skillTestCode, questionCode);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
