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
import com.lawencon.jobportal.candidate.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillInsertReqDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillGetResDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillInsertReqDto;
import com.lawencon.jobportal.candidate.service.UserSkillService;

@RestController
@RequestMapping("skills")
public class UserSkillController {

	@Autowired
	private UserSkillService userSkillService;

	@GetMapping
	public ResponseEntity<List<SkillGetResDto>> getAllSkill() {
		final List<SkillGetResDto> data = userSkillService.getAllSkill();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertSkill(@RequestBody SkillInsertReqDto data){
		final InsertResDto response = userSkillService.insertSkill(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/level")
	public ResponseEntity<List<LevelGetResDto>> getAllLevel() {
		final List<LevelGetResDto> data = userSkillService.getAllLevel();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping("/user")
	public ResponseEntity<InsertResDto> insertUserSkill(@RequestBody UserSkillInsertReqDto data){
		final InsertResDto response = userSkillService.insertUserSkill(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<UserSkillGetResDto>> getSkillByCandidate(@RequestParam String candidateId) {
		final List<UserSkillGetResDto> data = userSkillService.getByCandidate(candidateId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteUserSkill(@RequestParam String userSkillId){
		final Boolean result = userSkillService.deleteUserSkill(userSkillId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Skill Successfully Deleted.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Skill Failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	
}
