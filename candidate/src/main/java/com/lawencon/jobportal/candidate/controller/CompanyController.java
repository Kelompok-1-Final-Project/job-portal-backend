package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.company.CompanyGetResDto;
import com.lawencon.jobportal.candidate.dto.company.CompanyInsertReqDto;
import com.lawencon.jobportal.candidate.service.CompanyService;

@RestController
@RequestMapping("companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public ResponseEntity<List<CompanyGetResDto>> getAllCompany(){
		final List<CompanyGetResDto> response = companyService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertCompany(@RequestBody CompanyInsertReqDto data){
		final InsertResDto response = companyService.insertCompany(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
