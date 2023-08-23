package com.lawencon.jobportal.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.benefit.CityUpdateReqDto;
import com.lawencon.jobportal.candidate.dto.city.CityGetResDto;
import com.lawencon.jobportal.candidate.dto.city.CityInsertReqDto;
import com.lawencon.jobportal.candidate.service.CityService;


@RestController
@RequestMapping("cities")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertCity(@RequestBody CityInsertReqDto data){
		final InsertResDto response = cityService.insertCity(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<CityGetResDto>> getAllCity(){
		final List<CityGetResDto> response = cityService.getAllCity();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateCity(@RequestBody CityUpdateReqDto data){
		final UpdateResDto response = cityService.updateCity(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
