package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.userskill.UserSkillGetResDto;
import com.lawencon.jobportal.admin.dto.userskill.UserSkillInsertReqDto;
import com.lawencon.jobportal.admin.dto.userskill.UserSkillUpdateReqDto;
import com.lawencon.jobportal.admin.service.UserSkillService;

@RestController
@RequestMapping("skills")
public class UserSkillController {

	@Autowired
	private UserSkillService userSkillService;
	
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

	@PatchMapping("/level")
	public ResponseEntity<UpdateResDto> updateLevel(@RequestBody UserSkillUpdateReqDto data){
		final UpdateResDto response = userSkillService.updateLevel(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
