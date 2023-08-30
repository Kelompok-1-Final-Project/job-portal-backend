package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.report.ReportGetResDto;
import com.lawencon.jobportal.admin.service.ReportService;

@RestController
@RequestMapping("reports")
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@GetMapping()
	public ResponseEntity<List<ReportGetResDto>> getReport() {
		final List<ReportGetResDto> response = reportService.getReport();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
