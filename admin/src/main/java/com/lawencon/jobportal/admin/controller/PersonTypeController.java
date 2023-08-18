package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.persontype.PersonTypeGetResDto;
import com.lawencon.jobportal.admin.service.PersonTypeService;

@RestController
@RequestMapping("persontype")
public class PersonTypeController {
	
	@Autowired
	private PersonTypeService personTypeService;
	
	@GetMapping
	public ResponseEntity<List<PersonTypeGetResDto>> getAll(){
		final List<PersonTypeGetResDto> response = personTypeService.getAll();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
