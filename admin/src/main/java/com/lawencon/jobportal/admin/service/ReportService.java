package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.ReportDao;
import com.lawencon.jobportal.admin.dto.report.ReportGetResDto;

@Service
public class ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	public List<ReportGetResDto> getReport(String startDate, String endDate) {
		final List<ReportGetResDto> reportGetResDtos = new ArrayList<>();
		reportDao.getReport(startDate, endDate).forEach(r -> {
			final ReportGetResDto reportGetResDto = new ReportGetResDto();
			reportGetResDto.setCandidateName(r.getCandidateName());
			reportGetResDto.setJobName(r.getJobName());
			reportGetResDto.setEmploymentTypeName(r.getEmploymentTypeName());
			reportGetResDto.setDateDiff(r.getDateDiff());
			reportGetResDtos.add(reportGetResDto);
		});
		
		return reportGetResDtos;
	}
}
