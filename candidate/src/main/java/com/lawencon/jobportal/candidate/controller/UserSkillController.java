package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.candidate.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.candidate.dto.userskill.UserSkillGetResDto;
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

	@GetMapping("level")
	public ResponseEntity<List<LevelGetResDto>> getAllLevel() {
		final List<LevelGetResDto> data = userSkillService.getAllLevel();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("user")
	public ResponseEntity<List<UserSkillGetResDto>> getSkillByCandidate(@RequestParam String candidateId) {
		final List<UserSkillGetResDto> data = userSkillService.getByCandidate(candidateId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
