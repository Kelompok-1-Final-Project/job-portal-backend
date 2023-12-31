package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.BenefitDao;
import com.lawencon.jobportal.candidate.dao.CompanyDao;
import com.lawencon.jobportal.candidate.dao.EmploymentTypeDao;
import com.lawencon.jobportal.candidate.dao.JobBenefitDao;
import com.lawencon.jobportal.candidate.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.candidate.dao.JobDao;
import com.lawencon.jobportal.candidate.dao.JobPositionDao;
import com.lawencon.jobportal.candidate.dao.JobStatusDao;
import com.lawencon.jobportal.candidate.dao.QuestionDao;
import com.lawencon.jobportal.candidate.dao.SaveJobDao;
import com.lawencon.jobportal.candidate.dao.SkillTestDao;
import com.lawencon.jobportal.candidate.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.candidate.dto.DeleteResDto;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobBenefitReqDto;
import com.lawencon.jobportal.candidate.dto.job.JobGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.candidate.dto.job.JobPositionGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.candidate.dto.job.JobUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Benefit;
import com.lawencon.jobportal.candidate.model.Company;
import com.lawencon.jobportal.candidate.model.EmploymentType;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.JobBenefit;
import com.lawencon.jobportal.candidate.model.JobCandidateStatus;
import com.lawencon.jobportal.candidate.model.JobPosition;
import com.lawencon.jobportal.candidate.model.JobStatus;
import com.lawencon.jobportal.candidate.model.Question;
import com.lawencon.jobportal.candidate.model.SaveJob;
import com.lawencon.jobportal.candidate.model.SkillTest;
import com.lawencon.jobportal.candidate.model.SkillTestQuestion;
import com.lawencon.jobportal.candidate.util.DateConvert;

@Service
public class JobService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private SaveJobDao saveJobDao;

	@Autowired
	private JobStatusDao jobStatusDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private JobPositionDao jobPositionDao;

	@Autowired
	private EmploymentTypeDao employmentTypeDao;
	
	@Autowired
	private BenefitDao benefitDao;
	
	@Autowired
	private JobBenefitDao jobBenefitDao;
	
	@Autowired
	private SkillTestDao skillTestDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;
	
	@Autowired
	private SkillTestQuestionDao skillTestQuestionDao;
	
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
	
	public List<JobPositionGetResDto> getAllJobPosition() {
		final List<JobPositionGetResDto> jobPositionGetResDtos = new ArrayList<>();

		jobPositionDao.getAll(JobPosition.class).forEach(jp -> {
			final JobPositionGetResDto jobPositionGetResDto = new JobPositionGetResDto();
			jobPositionGetResDto.setId(jp.getId());
			jobPositionGetResDto.setPositionName(jp.getPositionName());
			jobPositionGetResDto.setPositionCode(jp.getPositionCode());
			jobPositionGetResDtos.add(jobPositionGetResDto);
		});

		return jobPositionGetResDtos;
	}
	
	public List<EmploymentTypeGetResDto> getAllEmploymentType() {
		final List<EmploymentTypeGetResDto> employmentTypeGetResDtos = new ArrayList<>();

		employmentTypeDao.getAll(EmploymentType.class).forEach(j -> {
			final EmploymentTypeGetResDto employmentTypeGetResDto = new EmploymentTypeGetResDto();
			employmentTypeGetResDto.setId(j.getId());
			employmentTypeGetResDto.setTypeName(j.getEmploymentName());
			employmentTypeGetResDto.setTypeCode(j.getEmploymentCode());
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
		job.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		job.setJobCode(data.getJobCode());
		
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
		
		final Job jobResult = jobDao.save(job);
		
		if(data.getBenefitCode() != null) {
			for (String b : data.getBenefitCode()) {
				final Benefit benefit = benefitDao.getByCode(b);
				
				final JobBenefit jobBenefit = new JobBenefit();
				jobBenefit.setBenefit(benefit);
				jobBenefit.setJob(jobResult);
				jobBenefitDao.save(jobBenefit);
			}
		}
		
		if(data.getTestName() != null && data.getTestName() != "") {
			final SkillTest skillTest = new SkillTest();
			skillTest.setTestCode(data.getTestCode());
			skillTest.setTestName(data.getTestName());
			skillTest.setJob(jobResult);
			final SkillTest skillTestResult = skillTestDao.save(skillTest);
			
			for (String q : data.getQuestionCode()) {
				System.out.print(q);
				final Question question = questionDao.getByCode(q);
				
				final SkillTestQuestion skillTestQuestion = new SkillTestQuestion();
				skillTestQuestion.setQuestion(question);
				skillTestQuestion.setSkillTest(skillTestResult);
				
				skillTestQuestionDao.save(skillTestQuestion);
			}
		}
		
		final InsertResDto result = new InsertResDto();
		result.setId(jobResult.getId());
		result.setMessage("Job added successfully");
		em().getTransaction().commit();
		return result;
	}

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
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());
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
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());
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
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());
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
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());
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
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}
	
	public List<JobGetResDto> getBySalay(Integer salaryStart, Integer salaryEnd) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getBySalary(salaryStart, salaryEnd).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());
			jobGetResDto.setVer(j.getVersion());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}
	
	public List<JobGetResDto> getByIndustry(String industry, Integer startIndex, Integer endIndex) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();
		final Integer totalJob = jobDao.getByIndustryNonPagination(industry).size();
		
		jobDao.getByIndustry(industry, startIndex, endIndex).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setCompanyPhoto(j.getCompany().getFile().getId());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			if(j.getUpdatedAt() != null) {
				jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());				
			}
			jobGetResDto.setIsBookmark(false);
			jobGetResDto.setVer(j.getVersion());
			jobGetResDto.setTotalJob(totalJob);		
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}
	
	public List<JobGetResDto> getByIndustryWithoutPagination(String industry) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();
		final Integer totalJob = jobDao.getByIndustryNonPagination(industry).size();
		
		jobDao.getByIndustryNonPagination(industry).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setCompanyPhoto(j.getCompany().getFile().getId());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			if(j.getUpdatedAt() != null) {
				jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());				
			}
			jobGetResDto.setIsBookmark(false);
			jobGetResDto.setVer(j.getVersion());
			jobGetResDto.setTotalJob(totalJob);		
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}
	
	public List<JobGetResDto> getFilterWithoutPagination(String name, String city, String position, List<String> employment, Integer salaryStart, Integer salaryEnd, String userId) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();
		final List<SaveJob> saveJob = saveJobDao.getByCandidateNonPagination(userId);
		final Integer totalJob = jobDao.filterSearchWithoutPagination(name, city, position, employment, salaryStart, salaryEnd).size();
		final List<JobCandidateStatus> jobCandidateStatus = jobCandidateStatusDao.getByCandidate(userId);
		
		jobDao.filterSearchWithoutPagination(name, city, position, employment, salaryStart, salaryEnd).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setCompanyPhoto(j.getCompany().getFile().getId());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			if(j.getUpdatedAt() != null) {
				jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());				
			}
			jobGetResDto.setVer(j.getVersion());
			jobGetResDto.setTotalJob(totalJob);
			jobGetResDto.setIsBookmark(false);
			jobGetResDto.setIsApply(false);
			
			for(SaveJob sj : saveJob) {
				if(sj.getJob().getId().equals(j.getId())) {
					jobGetResDto.setIsBookmark(true);
					jobGetResDto.setSaveJobId(sj.getId());
				}
			}
			
			for(JobCandidateStatus jcs : jobCandidateStatus) {
				if(jcs.getJob().getId().equals(j.getId())) {
					jobGetResDto.setIsApply(true);
				}
			}
			
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}
	
	public List<JobGetResDto> getFilter(String name, String city, String position, List<String> employment, Integer salaryStart, Integer salaryEnd, String userId, Integer startIndex, Integer endIndex) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();
		final List<SaveJob> saveJob = saveJobDao.getByCandidateNonPagination(userId);
		final Integer totalJob = jobDao.filterSearchWithoutPagination(name, city, position, employment, salaryStart, salaryEnd).size();
		final List<JobCandidateStatus> jobCandidateStatus = jobCandidateStatusDao.getByCandidate(userId);
		
		jobDao.filterSearch(name, city, position, employment, salaryStart, salaryEnd, startIndex, endIndex).forEach(j -> {
			final JobGetResDto jobGetResDto = new JobGetResDto();
			jobGetResDto.setId(j.getId());
			jobGetResDto.setJobTitle(j.getJobTitle());
			jobGetResDto.setSalaryStart(j.getSalaryStart());
			jobGetResDto.setSalaryEnd(j.getSalaryEnd());
			jobGetResDto.setDescription(j.getDescription());
			jobGetResDto.setEndDate(j.getEndDate().toString());
			jobGetResDto.setCompanyId(j.getCompany().getId());
			jobGetResDto.setCompanyName(j.getCompany().getCompanyName());
			jobGetResDto.setCompanyPhoto(j.getCompany().getFile().getId());
			jobGetResDto.setIndustryName(j.getCompany().getIndustry().getIndustryName());
			jobGetResDto.setCityName(j.getCompany().getCity().getCityName());
			jobGetResDto.setPositionName(j.getJobPosition().getPositionName());
			jobGetResDto.setStatusName(j.getJobStatus().getStatusName());
			jobGetResDto.setEmploymentName(j.getEmployementType().getEmploymentName());
			jobGetResDto.setCreatedAt(j.getCreatedAt().toString());
			if(j.getUpdatedAt() != null) {
				jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());				
			}
			jobGetResDto.setVer(j.getVersion());
			jobGetResDto.setTotalJob(totalJob);
			jobGetResDto.setIsBookmark(false);
			jobGetResDto.setIsApply(false);
			
			for(SaveJob sj : saveJob) {
				if(sj.getJob().getId().equals(j.getId())) {
					jobGetResDto.setIsBookmark(true);
					jobGetResDto.setSaveJobId(sj.getId());
				}
			}
			
			for(JobCandidateStatus jcs : jobCandidateStatus) {
				if(jcs.getJob().getId().equals(j.getId())) {
					jobGetResDto.setIsApply(true);
				}
			}
			
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}
	
	public JobGetResDto getJobByCode(String code, String candidateId) {
		final Job jobDb = jobDao.getByCode(code);
		final Job job = jobDao.getById(Job.class, jobDb.getId());

		final List<JobCandidateStatus> jobCandidateStatus = jobCandidateStatusDao.getByCandidate(candidateId);
		final List<SaveJob> saveJob = saveJobDao.getByCandidateNonPagination(candidateId);
		
		final JobGetResDto jobGetResDto = new JobGetResDto();
		jobGetResDto.setId(job.getId());
		jobGetResDto.setJobTitle(job.getJobTitle());
		jobGetResDto.setSalaryStart(job.getSalaryStart());
		jobGetResDto.setSalaryEnd(job.getSalaryEnd());
		jobGetResDto.setDescription(job.getDescription());
		jobGetResDto.setEndDate(job.getEndDate().toString());
		jobGetResDto.setCompanyId(job.getCompany().getId());
		jobGetResDto.setCompanyName(job.getCompany().getCompanyName());
		jobGetResDto.setCompanyPhoto(job.getCompany().getFile().getId());
		jobGetResDto.setIndustryName(job.getCompany().getIndustry().getIndustryName());
		jobGetResDto.setCityName(job.getCompany().getCity().getCityName());
		jobGetResDto.setPositionName(job.getJobPosition().getPositionName());
		jobGetResDto.setStatusName(job.getJobStatus().getStatusName());
		jobGetResDto.setEmploymentName(job.getEmployementType().getEmploymentName());
		jobGetResDto.setCreatedAt(job.getCreatedAt().toString());
		if(job.getUpdatedAt() != null) {
			jobGetResDto.setUpdatedAt(job.getUpdatedAt().toString());				
		}
		jobGetResDto.setVer(job.getVersion());
		jobGetResDto.setIsBookmark(false);
		jobGetResDto.setIsApply(false);
		
		for(SaveJob sj : saveJob) {
			if(sj.getJob().getId().equals(job.getId())) {
				jobGetResDto.setIsBookmark(true);
				jobGetResDto.setSaveJobId(sj.getId());
			}
		}
		
		for(JobCandidateStatus jcs : jobCandidateStatus) {
			if(jcs.getJob().getId().equals(job.getId())) {
				jobGetResDto.setIsApply(true);
			}
		}
		
		return jobGetResDto;
	}
	
	public JobGetResDto getById(String jobId, String candidateId) {
		final Job job = jobDao.getById(Job.class, jobId);

		final List<JobCandidateStatus> jobCandidateStatus = jobCandidateStatusDao.getByCandidate(candidateId);
		final List<SaveJob> saveJob = saveJobDao.getByCandidateNonPagination(candidateId);
		
		final JobGetResDto jobGetResDto = new JobGetResDto();
		jobGetResDto.setId(job.getId());
		jobGetResDto.setJobTitle(job.getJobTitle());
		jobGetResDto.setSalaryStart(job.getSalaryStart());
		jobGetResDto.setSalaryEnd(job.getSalaryEnd());
		jobGetResDto.setDescription(job.getDescription());
		jobGetResDto.setEndDate(job.getEndDate().toString());
		jobGetResDto.setCompanyId(job.getCompany().getId());
		jobGetResDto.setCompanyName(job.getCompany().getCompanyName());
		jobGetResDto.setCompanyPhoto(job.getCompany().getFile().getId());
		jobGetResDto.setIndustryName(job.getCompany().getIndustry().getIndustryName());
		jobGetResDto.setCityName(job.getCompany().getCity().getCityName());
		jobGetResDto.setPositionName(job.getJobPosition().getPositionName());
		jobGetResDto.setStatusName(job.getJobStatus().getStatusName());
		jobGetResDto.setEmploymentName(job.getEmployementType().getEmploymentName());
		jobGetResDto.setCreatedAt(job.getCreatedAt().toString());
		if(job.getUpdatedAt() != null) {
			jobGetResDto.setUpdatedAt(job.getUpdatedAt().toString());				
		}
		jobGetResDto.setVer(job.getVersion());
		jobGetResDto.setIsBookmark(false);
		jobGetResDto.setIsApply(false);
		
		for(SaveJob sj : saveJob) {
			if(sj.getJob().getId().equals(job.getId())) {
				jobGetResDto.setIsBookmark(true);
				jobGetResDto.setSaveJobId(sj.getId());
			}
		}
		
		for(JobCandidateStatus jcs : jobCandidateStatus) {
			if(jcs.getJob().getId().equals(job.getId())) {
				jobGetResDto.setIsApply(true);
			}
		}
		
		return jobGetResDto;
	}

	public UpdateResDto updateJob(JobUpdateReqDto data) {
		em().getTransaction().begin();

		final Job jobDb = jobDao.getByCode(data.getJobCode());
		final Job job = jobDao.getById(Job.class, jobDb.getId());
		job.setJobTitle(data.getJobTitle());
		job.setSalaryStart(data.getSalaryStart());
		job.setSalaryEnd(data.getSalaryEnd());
		job.setDescription(data.getDescription());
		job.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());
		
		final JobStatus jobStatus = jobStatusDao.getByCode(data.getJobStatusCode());
		job.setJobStatus(jobStatus);
		
		final JobPosition jobPosition = jobPositionDao.getByCode(data.getJobPositionCode());
		job.setJobPosition(jobPosition);
		
		final EmploymentType employmentType = employmentTypeDao.getByCode(data.getEmploymentTypeCode());
		job.setEmployementType(employmentType);

		final Job jobResult = jobDao.saveAndFlush(job);

		final UpdateResDto result = new UpdateResDto();
		result.setVersion(jobResult.getVersion());
		result.setMessage("Job updated successfully.");

		em().getTransaction().commit();
		return result;
	}
	
	public InsertResDto insertJobBenefit(JobBenefitReqDto data) {
		em().getTransaction().begin();
		
		final JobBenefit jobBenefit = new JobBenefit();
		
		final Benefit benefits = benefitDao.getByCode(data.getBenefitCode());
		final Benefit benefit = benefitDao.getById(Benefit.class, benefits.getId());
		jobBenefit.setBenefit(benefit);
		
		final Job jobs = jobDao.getByCode(data.getJobCode());
		final Job job = jobDao.getById(Job.class, jobs.getId());
		jobBenefit.setJob(job);
		
		final JobBenefit jobBenefitResult = jobBenefitDao.save(jobBenefit);
		
		final InsertResDto result = new InsertResDto();
		result.setId(jobBenefitResult.getId());
		result.setMessage("Insert Job Benefit Succesfully");
		
		em().getTransaction().commit();
		
		return result;
	}
	
	public DeleteResDto deleteJobBenefit(String jobCode, String benefitCode) {
		em().getTransaction().begin();

		final Job jobDb = jobDao.getByCode(jobCode);
		final Benefit benefitDb = benefitDao.getByCode(benefitCode);
		
		final JobBenefit jobBenefitDb = jobBenefitDao.getByJobAndBenefit(jobDb.getId(), benefitDb.getId());
		
		jobBenefitDao.deleteById(JobBenefit.class, jobBenefitDb.getId());

		final DeleteResDto result = new DeleteResDto();
		result.setMessage("Job Benefit Delete successfully.");

		em().getTransaction().commit();
		return result;
	}
	
	public DeleteResDto deleteSkillTestQuestion(String skillTestCode, String questionCode) {
		em().getTransaction().begin();

		final SkillTest skillTest = skillTestDao.getByCode(skillTestCode);
		final Question question = questionDao.getByCode(questionCode);
		
		final SkillTestQuestion skillTestQuestion = skillTestQuestionDao.getBySkillTestAndQuestion(skillTest.getId(), question.getId());
		
		skillTestQuestionDao.deleteById(SkillTestQuestion.class, skillTestQuestion.getId());

		final DeleteResDto result = new DeleteResDto();
		result.setMessage("Job Benefit Delete successfully.");

		em().getTransaction().commit();
		return result;
	}
}
