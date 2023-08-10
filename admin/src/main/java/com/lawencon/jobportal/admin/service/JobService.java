package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.JobStatusDao;
import com.lawencon.jobportal.admin.dto.job.JobGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.admin.model.JobStatus;

@Service
public class JobService {

	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private JobStatusDao jobStatusDao;

	public List<JobGetResDto> getByLocation(String location) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByLocation(location).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByCompany(String company) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByCompany(company).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByEmploymentType(String employmentType) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByEmploymentType(employmentType).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByPosition(String position) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByPosition(position).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByStatus(String status) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByStatus(status).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobStatusGetResDto> getAllJobStatus() {
		final List<JobStatusGetResDto> jobStatusGetResDtos = new ArrayList<>();

		jobStatusDao.getAll(JobStatus.class).forEach(j -> {
			final JobStatusGetResDto jobStatusGetResDto = new JobStatusGetResDto();
			jobStatusGetResDto.setId(j.getId());
			jobStatusGetResDto.setStatusName(j.getStatusName());
			jobStatusGetResDto.setStatusCode(j.getStatusCode());
			jobStatusGetResDtos.add(jobStatusGetResDto);
		});
		return jobStatusGetResDtos;
	}
	
	public JobStatusGetResDto getJobStatusByCode(String code) {
		final JobStatus jobStatus = jobStatusDao.getByCode(code);
		
		final JobStatusGetResDto jobStatusGetResDto = new JobStatusGetResDto();
		jobStatusGetResDto.setId(jobStatus.getId());
		jobStatusGetResDto.setStatusName(jobStatus.getStatusName());
		jobStatusGetResDto.setStatusCode(jobStatus.getStatusCode());

		return jobStatusGetResDto;
	}

}
