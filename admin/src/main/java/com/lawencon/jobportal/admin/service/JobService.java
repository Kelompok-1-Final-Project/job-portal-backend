package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.admin.dao.BenefitDao;
import com.lawencon.jobportal.admin.dao.CompanyDao;
import com.lawencon.jobportal.admin.dao.EmploymentTypeDao;
import com.lawencon.jobportal.admin.dao.JobBenefitDao;
import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.JobPositionDao;
import com.lawencon.jobportal.admin.dao.JobStatusDao;
import com.lawencon.jobportal.admin.dao.QuestionDao;
import com.lawencon.jobportal.admin.dao.SkillTestDao;
import com.lawencon.jobportal.admin.dao.SkillTestQuestionDao;
import com.lawencon.jobportal.admin.dao.UserDao;
import com.lawencon.jobportal.admin.dto.DeleteResDto;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.job.EmploymentTypeGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobInsertReqDto;
import com.lawencon.jobportal.admin.dto.job.JobStatusGetResDto;
import com.lawencon.jobportal.admin.dto.job.JobUpdateReqDto;
import com.lawencon.jobportal.admin.dto.jobposition.JobPositionGetResDto;
import com.lawencon.jobportal.admin.model.Benefit;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.EmploymentType;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobBenefit;
import com.lawencon.jobportal.admin.model.JobPosition;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.Question;
import com.lawencon.jobportal.admin.model.SkillTest;
import com.lawencon.jobportal.admin.model.SkillTestQuestion;
import com.lawencon.jobportal.admin.model.User;
import com.lawencon.jobportal.admin.util.DateConvert;
import com.lawencon.jobportal.admin.util.GeneratorId;

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
	
	@Autowired
	private BenefitDao benefitDao;
	
	@Autowired
	private JobBenefitDao jobBenefitDao;

	@Autowired
	private SkillTestDao skillTestDao;
	
	@Autowired
	private SkillTestQuestionDao skillTestQuestionDao;
	
	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private RestTemplate restTemplate;

	public List<JobGetResDto> getAll() {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getAll(Job.class).forEach(j -> {
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
			if(j.getUpdatedAt()!=null) {
				jobGetResDto.setUpdatedAt(j.getUpdatedAt().toString());				
			}
			jobGetResDto.setVer(j.getVersion());
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByLocation(String location, Integer startPosition, Integer endPosition) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByLocation(location, startPosition, endPosition).forEach(j -> {
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByName(String jobName, Integer startPosition, Integer endPosition) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByName(jobName, startPosition, endPosition).forEach(j -> {
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByIdustry(String jobName, Integer startPosition, Integer endPosition) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByName(jobName, startPosition, endPosition).forEach(j -> {
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByEmploymentType(String employmentType, Integer startPosition, Integer endPosition) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByEmploymentType(employmentType, startPosition, endPosition).forEach(j -> {
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByPosition(String position, Integer startPosition, Integer endPosition) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByPosition(position, startPosition, endPosition).forEach(j -> {
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
			jobGetResDtos.add(jobGetResDto);
		});

		return jobGetResDtos;
	}

	public List<JobGetResDto> getByStatus(String status, Integer startPosition, Integer endPosition) {
		final List<JobGetResDto> jobGetResDtos = new ArrayList<>();

		jobDao.getByStatus(status, startPosition, endPosition).forEach(j -> {
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
			jobGetResDto.setInterviewerName(j.getInterviewer().getProfile().getFullName());
			jobGetResDto.setHrName(j.getHr().getProfile().getFullName());
			jobGetResDto.setJobCode(j.getJobCode());
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
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Job job = new Job();
			final String jobCode = GeneratorId.generateCode();
			data.setJobCode(jobCode);
			job.setJobCode(jobCode);
			job.setJobTitle(data.getJobTitle());
			job.setSalaryStart(data.getSalaryStart());
			job.setSalaryEnd(data.getSalaryEnd());
			job.setDescription(data.getDescription());
			job.setEndDate(DateConvert.convertDate(data.getEndDate()).toLocalDate());

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
			final EmploymentType employmentTypeResult = employmentTypeDao.getById(EmploymentType.class,
					employmentType.getId());
			job.setEmployementType(employmentTypeResult);

			final User hr = userDao.getById(User.class, data.getHrId());
			job.setHr(hr);

			final User interviewer = userDao.getById(User.class, data.getInterviewerId());
			job.setInterviewer(interviewer);

			final Job jobResult = jobDao.save(job);
			
			for (String b : data.getBenefitCode()) {
				final Benefit benefit = benefitDao.getByCode(b);
				
				final JobBenefit jobBenefit = new JobBenefit();
				jobBenefit.setBenefit(benefit);
				jobBenefit.setJob(jobResult);
				jobBenefitDao.save(jobBenefit);
			}
			
			if(data.getTestName() != null && data.getTestName() != "") {
				final SkillTest skillTest = new SkillTest();
				final String skillTestCode = GeneratorId.generateCode();
				data.setTestCode(skillTestCode);
				skillTest.setTestCode(skillTestCode);
				skillTest.setTestName(data.getTestName());
				skillTest.setJob(jobResult);
				final SkillTest skillTestResult = skillTestDao.save(skillTest);
				final List<String> questionCode = new ArrayList<>();
				
				for (String q : data.getQuestionId()) {
					final Question question = questionDao.getById(Question.class, q);
					questionCode.add(question.getQuestionCode());
					final SkillTestQuestion skillTestQuestion = new SkillTestQuestion();
					skillTestQuestion.setQuestion(question);
					skillTestQuestion.setSkillTest(skillTestResult);
					
					skillTestQuestionDao.save(skillTestQuestion);
				}
				
				data.setQuestionCode(questionCode);
			}

			final String jobInsertCandidateAPI = "http://localhost:8081/jobs";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<JobInsertReqDto> jobInsert = RequestEntity.post(jobInsertCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(jobInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(jobResult.getId());
				result.setMessage("Job added successfully");

				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public UpdateResDto updateJob(JobUpdateReqDto data) {
		final UpdateResDto result = new UpdateResDto();
		try {
			em().getTransaction().begin();

			final Job job = jobDao.getById(Job.class, data.getJobId());
			data.setJobCode(job.getJobCode());
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

			final User hr = userDao.getById(User.class, data.getHrId());
			job.setHr(hr);

			final User interviewer = userDao.getById(User.class, data.getInterviewerId());
			job.setInterviewer(interviewer);

			final Job jobResult = jobDao.saveAndFlush(job);

			final String jobUpdateCandidateAPI = "http://localhost:8081/jobs";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<JobUpdateReqDto> companyUpdate = RequestEntity.patch(jobUpdateCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(companyUpdate, UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				result.setVersion(jobResult.getVersion());
				result.setMessage("Job updated successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}
			
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
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
			employmentTypeGetResDto.setTypeCode(j.getEmploymentCode());
			employmentTypeGetResDtos.add(employmentTypeGetResDto);
		});
		return employmentTypeGetResDtos;
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

	public JobGetResDto getById(String jobId) {
		final Job job = jobDao.getById(Job.class, jobId);

		final JobGetResDto jobGetResDto = new JobGetResDto();
		jobGetResDto.setId(job.getId());
		jobGetResDto.setJobTitle(job.getJobTitle());
		jobGetResDto.setSalaryStart(job.getSalaryStart());
		jobGetResDto.setSalaryEnd(job.getSalaryEnd());
		jobGetResDto.setDescription(job.getDescription());
		jobGetResDto.setEndDate(job.getEndDate().toString());
		jobGetResDto.setCompanyId(job.getCompany().getId());
		jobGetResDto.setCompanyName(job.getCompany().getCompanyName());
		jobGetResDto.setIndustryName(job.getCompany().getIndustry().getIndustryName());
		jobGetResDto.setCityName(job.getCompany().getCity().getCityName());
		jobGetResDto.setPositionName(job.getJobPosition().getPositionName());
		jobGetResDto.setStatusName(job.getJobStatus().getStatusName());
		jobGetResDto.setEmploymentName(job.getEmployementType().getEmploymentName());
		jobGetResDto.setCreatedAt(job.getCreatedAt().toString());
		if(job.getUpdatedAt()!=null) {
			jobGetResDto.setUpdatedAt(job.getUpdatedAt().toString());			
		}
		jobGetResDto.setVer(job.getVersion());
		jobGetResDto.setInterviewerName(job.getInterviewer().getProfile().getFullName());
		jobGetResDto.setHrId(job.getHr().getId());
		jobGetResDto.setInterviewerId(job.getInterviewer().getId());
		jobGetResDto.setHrName(job.getHr().getProfile().getFullName());
		jobGetResDto.setJobCode(job.getJobCode());

		return jobGetResDto;
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
