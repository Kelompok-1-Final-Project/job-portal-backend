package com.lawencon.jobportal.admin.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CompanyDao;
import com.lawencon.jobportal.admin.dao.EmploymentTypeDao;
import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.JobPositionDao;
import com.lawencon.jobportal.admin.dao.JobStatusDao;
import com.lawencon.jobportal.admin.dao.UserDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.admin.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobUpdateReqDto;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.EmploymentType;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobPosition;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.User;

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
	
	@Autowired
	private UserDao userDao;
	
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
		
		final User hr = userDao.getById(User.class, data.getHrId());
		job.setHr(hr);
		
		final User interviewer = userDao.getById(User.class, data.getInterviewerId());
		job.setInterviewer(interviewer);
		
		final Job jobResult = jobDao.save(job);
		
		final InsertResDto result = new InsertResDto();
		result.setId(jobResult.getId());
		result.setMessage("Job added successfully");
		
		em().getTransaction().commit();
		return result;
	}
	
	public UpdateResDto updateJob(JobUpdateReqDto data) {
		em().getTransaction().begin();
		
		final Job job = jobDao.getById(Job.class, data.getJobId());
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
		
		final User hr = userDao.getById(User.class, data.getHrId());
		job.setHr(hr);
		
		final User interviewer = userDao.getById(User.class, data.getInterviewerId());
		job.setInterviewer(interviewer);
		
		final Job jobResult = jobDao.saveAndFlush(job);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(jobResult.getVersion());
		result.setMessage("Job updated successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public Boolean deleteJob(String jobId) {
		em().getTransaction().begin();
		
		final Boolean result = jobDao.deleteById(Job.class, jobId);
		
		em().getTransaction().commit();
		return result;
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

}
