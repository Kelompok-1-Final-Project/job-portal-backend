package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.industry.IndustryGetResDto;
import com.lawencon.jobportal.candidate.service.IndustryService;

@RestController
@RequestMapping("industries")
public class IndustryController {

	@Autowired
	private IndustryService industryService;
	
	@GetMapping
	public ResponseEntity<List<IndustryGetResDto>> getAllIndustry() {
		final List<IndustryGetResDto>data = industryService.getAllIndustry();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("search")
    public ResponseEntity<IndustryGetResDto> getIndustryByName(@RequestParam String data) {
		final IndustryGetResDto response = industryService.getByName(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
   }

}
