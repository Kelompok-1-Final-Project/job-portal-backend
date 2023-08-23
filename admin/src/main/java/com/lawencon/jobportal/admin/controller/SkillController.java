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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.skill.LevelGetResDto;
import com.lawencon.jobportal.admin.dto.skill.SkillGetResDto;
import com.lawencon.jobportal.admin.dto.skill.SkillInsertReqDto;
import com.lawencon.jobportal.admin.dto.skill.SkillUpdateReqDto;
import com.lawencon.jobportal.admin.service.SkillService;

@RestController
@RequestMapping("skills")
public class SkillController {

	@Autowired
	private SkillService skillService;

	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody SkillInsertReqDto data) {
		final InsertResDto response = skillService.insert(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/candidate-insert")
	public ResponseEntity<InsertResDto> insertFromCandidate(@RequestBody SkillInsertReqDto data) {
		final InsertResDto response = skillService.insertFromCandidate(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<SkillGetResDto>> getAllSkill() {
		final List<SkillGetResDto> data = skillService.getAllSkill();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("search")
	public ResponseEntity<SkillGetResDto> getSkillByName(@RequestParam String data) {
		final SkillGetResDto response = skillService.getByName(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody SkillUpdateReqDto data) {
		final UpdateResDto response = skillService.update(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("{code}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("code") String code) {
		final DeleteResDto result = skillService.deleteByCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("level")
	public ResponseEntity<List<LevelGetResDto>> getAllLevel(){
		final List<LevelGetResDto> response = skillService.getAllLevel();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
