package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawencon.jobportal.candidate.dao.SaveJobDao;
import com.lawencon.jobportal.candidate.dto.savejob.SaveJobGetResDto;

public class SaveJobService {
	@Autowired
	private SaveJobDao saveJobDao;
	
	public List<SaveJobGetResDto> getByCandidate(String candidateId) {
		final List<SaveJobGetResDto> saveJobGetResDtos = new ArrayList<>();

		saveJobDao.getByCandidate(candidateId).forEach(sj -> {
			final SaveJobGetResDto saveJobGetResDto = new SaveJobGetResDto();
			saveJobGetResDto.setId(sj.getId());
			saveJobGetResDto.setCompanyName(sj.getJob().getCompany().getCompanyName());
			saveJobGetResDto.setDescription(sj.getJob().getDescription());
			saveJobGetResDto.setEmploymentName(sj.getJob().getEmployementType().getEmploymentName());
			saveJobGetResDto.setEndDate(sj.getJob().getEndDate().toString());
			saveJobGetResDto.setJobTitle(sj.getJob().getJobTitle());
			saveJobGetResDto.setPositionName(sj.getJob().getJobPosition().getPositionName());
			saveJobGetResDto.setSalaryEnd(sj.getJob().getSalaryEnd());
			saveJobGetResDto.setSalaryStart(sj.getJob().getSalaryStart());
			saveJobGetResDto.setStatusName(sj.getJob().getJobStatus().getStatusName());
			saveJobGetResDtos.add(saveJobGetResDto);
		});

		return saveJobGetResDtos;
	}
}
