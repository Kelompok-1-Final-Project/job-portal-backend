package com.lawencon.jobportal.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.admin.dto.report.ReportGetResDto;
import com.lawencon.jobportal.admin.service.ReportService;

@RestController
@RequestMapping("reports")
public class ReportController {
	@Autowired
	private ReportService reportService;
	
	@GetMapping
	public ResponseEntity<List<ReportGetResDto>> getReport(@RequestParam("start") String startDate, 
			@RequestParam("end") String endDate) {
		final List<ReportGetResDto> response = reportService.getReport(startDate, endDate);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/download")	
    public ResponseEntity<?> downloadReport(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
        final String fileName = "Application Report";
        final byte[] reportByteArr = reportService.downloadReport(startDate, endDate);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
                .body(reportByteArr);
   }
	
}
