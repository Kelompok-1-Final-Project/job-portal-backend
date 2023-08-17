package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.admin.dto.job.JobGetResDto;

@Service
public class JobCandidateStatusService {
	
	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;
	
	public List<JobGetResDto> getByJob(String jobCode){
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();
		
		jobCandidateStatusDao.getByJob(jobCode).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getJob().getId());
			jobGetResDto.setJobTitle(j.getJob().getJobTitle());
			jobGetResDto.setSalaryStart(j.getJob().getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getJob().getSalaryEnd());
			jobGetResDto.setDescription(j.getJob().getDescription());
			jobGetResDto.setEndDate(j.getJob().getEndDate().toString());
			jobGetResDto.setCompanyName(j.getJob().getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getJob().getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getJob().getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJob().getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJob().getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getJob().getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getJob().getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getJob().getUpdatedAt().toString());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});
		
		return jobGetResDtos;
	}
}
