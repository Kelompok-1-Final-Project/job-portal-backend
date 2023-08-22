package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressGetResDto;

@Service
public class JobCandidateStatusService {
	
	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;
	
	public List<CandidateProgressGetResDto> getByJob(String jobCode){
		final List<CandidateProgressGetResDto> data = new ArrayList<>();
		
		jobCandidateStatusDao.getByJob(jobCode).forEach(j -> {
			final CandidateProgressGetResDto candidateProgressGetResDto = new CandidateProgressGetResDto();
			candidateProgressGetResDto.setCandidateId(j.getCandidate().getId());
			candidateProgressGetResDto.setCandidateName(j.getCandidate().getCandidateProfile().getFullName());
			candidateProgressGetResDto.setCandidateProgressId(j.getId());
			candidateProgressGetResDto.setJobId(j.getJob().getId());
			candidateProgressGetResDto.setJobName(j.getJob().getJobTitle());
			candidateProgressGetResDto.setStatusCode(j.getStatus().getProcessCode());
			candidateProgressGetResDto.setStatusName(j.getStatus().getProcessName());
			data.add(candidateProgressGetResDto);
		});
		
		return data;
	}
	
}
