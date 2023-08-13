package com.lawencon.jobportal.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.candidate.service.BenefitService;

@RestController
@RequestMapping("benefits")
public class BenefitController {
	
	@Autowired
	private BenefitService benefitService;
	

}
