package com.lawencon.jobportal.candidate.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.CompanyDao;
import com.lawencon.jobportal.candidate.dao.EmploymentTypeDao;
import com.lawencon.jobportal.candidate.dao.JobDao;
import com.lawencon.jobportal.candidate.dao.JobPositionDao;
import com.lawencon.jobportal.candidate.dao.JobStatusDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.candidate.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.candidate.model.Company;
import com.lawencon.jobportal.candidate.model.EmploymentType;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.JobPosition;
import com.lawencon.jobportal.candidate.model.JobStatus;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class JobService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private JobDao jobDao;

	@Autowired
	private JobStatusDao jobStatusDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private JobPositionDao jobPositionDao;

	@Autowired
	private EmploymentTypeDao employmentTypeDao;
	
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
	
	public List<EmploymentTypeGetResDto> getAllEmploymentType() {
		final List<EmploymentTypeGetResDto> employmentTypeGetResDtos = new ArrayList<>();

		employmentTypeDao.getAll(EmploymentType.class).forEach(j -> {
			final EmploymentTypeGetResDto employmentTypeGetResDto = new EmploymentTypeGetResDto();
			employmentTypeGetResDto.setId(j.getId());
			employmentTypeGetResDto.setTypeName(j.getEmploymentName());
			employmentTypeGetResDto.setTypeCode(j.getEmploymentName());
			employmentTypeGetResDtos.add(employmentTypeGetResDto);
		});
		return employmentTypeGetResDtos;
	}
	
	public InsertResDto insertJob(JobInsertReqDto data) {
		em().getTransaction().begin();
		
		final Job job = new Job();
		job.setJobTitle(data.getJobTitle());
		job.setSalaryStart(data.getSalaryStart());
		job.setSalaryEnd(data.getSalaryEnd());
		job.setDescription(data.getDescription());
		job.setEndDate(LocalDate.parse(data.getEndDate()));
		
		final Company companyDb = companyDao.getByCode(data.getCompanyCode());
		final Company companyResult = companyDao.getById(Company.class, companyDb.getId());
		job.setCompany(companyResult);
		
		final JobPosition jobPositionDb = jobPositionDao.getByCode(data.getJobPositionCode());
		final JobPosition jobPositionResult = jobPositionDao.getById(JobPosition.class, jobPositionDb.getId());
		job.setJobPosition(jobPositionResult);
		
		final JobStatus jobStatus = jobStatusDao.getByCode(data.getJobStatusCode());
		final JobStatus jobStatusResult = jobStatusDao.getById(JobStatus.class, jobStatus.getId());
		job.setJobStatus(jobStatusResult);
		
		final EmploymentType employmentType = employmentTypeDao.getByCode(data.getEmploymentCode());
		final EmploymentType employmentTypeResult = employmentTypeDao.getById(EmploymentType.class, employmentType.getId());
		job.setEmployementType(employmentTypeResult);
		
		final User hr = new User();
		hr.setId(data.getHrId());
		job.setHr(hr);
		
		final User interviewer = new User();
		interviewer.setId(data.getInterviewerId());
		job.setInterviewer(interviewer);
		
		final Job jobResult = jobDao.save(job);
		
		final InsertResDto result = new InsertResDto();
		result.setId(jobResult.getId());
		result.setMessage("Job added successfully");
		
		em().getTransaction().commit();
		return result;
	}

}
