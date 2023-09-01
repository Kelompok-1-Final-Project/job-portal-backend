package com.lawencon.jobportal.admin.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.ReportDao;
import com.lawencon.jobportal.admin.dto.report.ReportGetResDto;
import com.lawencon.jobportal.admin.dto.report.ReportJasperGetReqDto;
import com.lawencon.util.JasperUtil;

@Service
public class ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private JasperUtil jasperUtil;
	
	public List<ReportGetResDto> getReport(String startDate, String endDate) {
		final List<ReportGetResDto> reportGetResDtos = new ArrayList<>();
		LocalDate startDateLocal = null;
		LocalDate endDateLocal = null;
		if(startDate != null && !startDate.equals("undefined")) {
			startDateLocal = Date.valueOf(startDate).toLocalDate();	
		}
		if(endDate != null && !endDate.equals("undefined")){
			endDateLocal = Date.valueOf(endDate).toLocalDate();		
		}
		
		reportDao.getReport(startDateLocal, endDateLocal).forEach(r -> {
			final ReportGetResDto reportGetResDto = new ReportGetResDto();
			reportGetResDto.setCandidateName(r.getCandidateName());
			reportGetResDto.setJobName(r.getJobName());
			reportGetResDto.setEmploymentTypeName(r.getEmploymentTypeName());
			reportGetResDto.setDateDiff(r.getDateDiff());
			reportGetResDtos.add(reportGetResDto);
		});
		
		return reportGetResDtos;
	}
	
	public byte[] downloadReport(String startDate, String endDate) {
		try {
			final Map<String, Object> reportData = new HashMap<>();
			
			final LocalDate now = LocalDate.now();
			
			reportData.put("today", now.toString());
			reportData.put("startDate", startDate);
			reportData.put("endDate", endDate);

			final List<ReportJasperGetReqDto> reportJasperGetReqDtos = new ArrayList<>();
			
			LocalDate startDateLocal = null;
			LocalDate endDateLocal = null;
			if(startDate != null && !startDate.equals("undefined")) {
				startDateLocal = Date.valueOf(startDate).toLocalDate();	
			}
			if(endDate != null && !endDate.equals("undefined")){
				endDateLocal = Date.valueOf(endDate).toLocalDate();		
			}
			
			reportDao.getReport(startDateLocal, endDateLocal).forEach(r -> {
				final ReportJasperGetReqDto reportJasperGetReqDto = new ReportJasperGetReqDto();
				reportJasperGetReqDto.setFullName(r.getCandidateName());
				reportJasperGetReqDto.setJobTitle(r.getJobName());
				reportJasperGetReqDto.setEmploymentName(r.getEmploymentTypeName());
				reportJasperGetReqDto.setDateDiff(r.getDateDiff());
				reportJasperGetReqDtos.add(reportJasperGetReqDto);
			});
			
			
			final byte[] reportByteArr = jasperUtil.responseToByteArray(reportJasperGetReqDtos, reportData, "reportHired");
			
			return reportByteArr;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
