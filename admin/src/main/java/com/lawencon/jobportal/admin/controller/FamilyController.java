package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.relationship.RelationshipGetResDto;
import com.lawencon.jobportal.admin.service.FamilyService;

@RestController
@RequestMapping("families")
public class FamilyController {
	
	@Autowired
	private FamilyService familyService;
	
	@GetMapping
	public ResponseEntity<List<RelationshipGetResDto>> getAllRelationship(){
		final List<RelationshipGetResDto> response = familyService.getAllRelationship();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteFamily(@RequestParam String familyId){
		final Boolean result = familyService.deleteFamily(familyId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Delete Family Successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Family Failed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
