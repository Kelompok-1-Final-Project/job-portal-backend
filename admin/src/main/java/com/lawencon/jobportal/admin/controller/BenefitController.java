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
import com.lawencon.jobportal.admin.dto.benefit.BenefitGetResDto;
import com.lawencon.jobportal.admin.dto.benefit.BenefitInsertReqDto;
import com.lawencon.jobportal.admin.dto.benefit.BenefitUpdateReqDto;
import com.lawencon.jobportal.admin.service.BenefitService;

@RestController
@RequestMapping("benefits")
public class BenefitController {
	

	@Autowired
	private BenefitService benefitService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody BenefitInsertReqDto data) {
		final InsertResDto response = benefitService.insert(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<BenefitGetResDto>> getAllbenefit() {
		final List<BenefitGetResDto>data = benefitService.getAllBenefit();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("search")
    public ResponseEntity<BenefitGetResDto> getBenefitByName(@RequestParam String data) {
		final BenefitGetResDto response = benefitService.getByName(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
   }
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody BenefitUpdateReqDto data) {
		final UpdateResDto response = benefitService.update(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("{code}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("code") String code) {
		final DeleteResDto result = benefitService.deleteByCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
