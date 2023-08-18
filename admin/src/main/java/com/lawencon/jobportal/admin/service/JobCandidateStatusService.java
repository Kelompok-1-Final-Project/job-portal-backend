package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.admin.dto.jobcandidatestatus.JobCandidateStatusGetResDto;

@Service
public class JobCandidateStatusService {
	
	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;
	
	public List<JobCandidateStatusGetResDto> getByJob(String jobCode){
		final List<JobCandidateStatusGetResDto> data = new ArrayList<>();
		
		jobCandidateStatusDao.getByJob(jobCode).forEach(j -> {
			final JobCandidateStatusGetResDto candidateStatusGetResDto = new JobCandidateStatusGetResDto();
			candidateStatusGetResDto.setJobCandidateStatusId(j.getId());
			candidateStatusGetResDto.setCandidateName(j.getCandidate().getCandidateProfile().getFullName());
			candidateStatusGetResDto.setStatusCode(j.getStatus().getProcessCode());
			candidateStatusGetResDto.setStatusName(j.getStatus().getProcessName());
			data.add(candidateStatusGetResDto);
		});
		
		return data;
	}
	
}
