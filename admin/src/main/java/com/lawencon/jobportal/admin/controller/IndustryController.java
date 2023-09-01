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
import com.lawencon.jobportal.admin.dto.industry.IndustryGetResDto;
import com.lawencon.jobportal.admin.dto.industry.IndustryInsertReqDto;
import com.lawencon.jobportal.admin.dto.industry.IndustryUpdateReqDto;
import com.lawencon.jobportal.admin.service.IndustryService;

@RestController
@RequestMapping("industries")
public class IndustryController {

	@Autowired
	private IndustryService industryService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody IndustryInsertReqDto data) {
		final InsertResDto response = industryService.insert(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<IndustryGetResDto>> getAllIndustry() {
		final List<IndustryGetResDto> data = industryService.getAllIndustry();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("search")
    public ResponseEntity<IndustryGetResDto> getIndustryByName(@RequestParam String data) {
		final IndustryGetResDto response = industryService.getByName(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
   }
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody IndustryUpdateReqDto data) {
		final UpdateResDto response = industryService.update(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("{code}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("code") String code) {
		final DeleteResDto result = industryService.deleteByCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
