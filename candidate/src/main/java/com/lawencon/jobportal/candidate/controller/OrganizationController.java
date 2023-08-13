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
import com.lawencon.jobportal.candidate.dto.organization.OrganizationGetResDto;
import com.lawencon.jobportal.candidate.dto.organization.OrganizationInsertReqDto;
import com.lawencon.jobportal.candidate.service.OrganizationService;

@RestController
@RequestMapping("organizations")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	
	@GetMapping
	public ResponseEntity<List<OrganizationGetResDto>> getByCandidate(@RequestParam String candidateId){
		final List<OrganizationGetResDto> data = organizationService.getByCandidate(candidateId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertOrganization(@RequestBody OrganizationInsertReqDto data){
		final InsertResDto response = organizationService.insertOrganization(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteResDto> deleteOrganization(@RequestParam String organizationId){
		final Boolean result = organizationService.deleteOrganization(organizationId);
		final DeleteResDto response = new DeleteResDto();
		if(result) {
			response.setMessage("Organization Sucessfully Deleted");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("Delete Organization Failed.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
