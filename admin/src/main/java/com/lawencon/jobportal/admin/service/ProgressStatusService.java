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
import com.lawencon.jobportal.admin.constant.StatusCodeEnum;
import com.lawencon.jobportal.admin.dao.ApplicationDao;
import com.lawencon.jobportal.admin.dao.AssessmentDao;
import com.lawencon.jobportal.admin.dao.CandidateDao;
import com.lawencon.jobportal.admin.dao.EmployeeDao;
import com.lawencon.jobportal.admin.dao.FileDao;
import com.lawencon.jobportal.admin.dao.HiredDao;
import com.lawencon.jobportal.admin.dao.InterviewDao;
import com.lawencon.jobportal.admin.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.admin.dao.JobDao;
import com.lawencon.jobportal.admin.dao.MedicalCheckupDao;
import com.lawencon.jobportal.admin.dao.OfferingDao;
import com.lawencon.jobportal.admin.dao.StatusProcessDao;
import com.lawencon.jobportal.admin.dao.UserDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.application.ApplicationGetResDto;
import com.lawencon.jobportal.admin.dto.application.ApplicationInsertReqDto;
import com.lawencon.jobportal.admin.dto.assessment.AssessmentGetResDto;
import com.lawencon.jobportal.admin.dto.assessment.AssessmentInsertReqDto;
import com.lawencon.jobportal.admin.dto.assessment.AssessmentUpdateReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressGetResDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressInsertReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressUpdateByCodeReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressUpdateReqDto;
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateStageProcessResDto;
import com.lawencon.jobportal.admin.dto.hired.HiredGetResDto;
import com.lawencon.jobportal.admin.dto.hired.HiredInsertReqDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewGetResDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewInsertReqDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewUpdateReqDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupGetResDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupInsertReqDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupUpdateReqDto;
import com.lawencon.jobportal.admin.dto.offering.OfferingGetResDto;
import com.lawencon.jobportal.admin.dto.offering.OfferingInsertReqDto;
import com.lawencon.jobportal.admin.dto.progress.StatusProgressGetResDto;
import com.lawencon.jobportal.admin.model.Application;
import com.lawencon.jobportal.admin.model.Assessment;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.Employee;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Hired;
import com.lawencon.jobportal.admin.model.Interview;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobCandidateStatus;
import com.lawencon.jobportal.admin.model.MedicalCheckup;
import com.lawencon.jobportal.admin.model.Offering;
import com.lawencon.jobportal.admin.model.StatusProcess;
import com.lawencon.jobportal.admin.model.User;
import com.lawencon.jobportal.admin.util.DateConvert;

@Service
public class ProgressStatusService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private StatusProcessDao statusProcessDao;

	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	private AssessmentDao assessmentDao;

	@Autowired
	private MedicalCheckupDao medicalCheckupDao;

	@Autowired
	private InterviewDao interviewDao;

	@Autowired
	private HiredDao hiredDao;

	@Autowired
	private OfferingDao offeringDao;

	@Autowired
	private CandidateDao candidateDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private FileDao fileDao;

	@Autowired
	private RestTemplate restTemplate;

	public List<StatusProgressGetResDto> getAllProgressStatus() {
		final List<StatusProcess> listStatus = statusProcessDao.getAll(StatusProcess.class);
		final List<StatusProgressGetResDto> listResult = new ArrayList<>();
		for (StatusProcess sp : listStatus) {
			final StatusProgressGetResDto result = new StatusProgressGetResDto();
			result.setProgressId(sp.getId());
			result.setProgressCode(sp.getProcessCode());
			result.setProgressName(sp.getProcessName());
			listResult.add(result);
		}
		return listResult;
	}

	public List<CandidateProgressGetResDto> getAllCandidateProgress() {
		final List<JobCandidateStatus> listProgress = jobCandidateStatusDao.getAll(JobCandidateStatus.class);
		final List<CandidateProgressGetResDto> listResult = new ArrayList<>();
		for (JobCandidateStatus p : listProgress) {
			final CandidateProgressGetResDto result = new CandidateProgressGetResDto();
			result.setCandidateProgressId(p.getId());
			result.setCandidateId(p.getCandidate().getId());
			result.setCandidateName(p.getCandidate().getCandidateProfile().getFullName());
			result.setJobId(p.getJob().getId());
			result.setJobName(p.getJob().getJobTitle());
			result.setStatusCode(p.getStatus().getProcessCode());
			result.setStatusName(p.getStatus().getProcessName());
			listResult.add(result);
		}
		return listResult;
	}

	public List<ApplicationGetResDto> getAllApplication(String userId) {
		final List<Application> listApplication = applicationDao.getByUser(userId);
		final List<ApplicationGetResDto> listResult = new ArrayList<>();
		for (Application a : listApplication) {
			final ApplicationGetResDto result = new ApplicationGetResDto();
			result.setApplicationId(a.getId());
			result.setCandidateId(a.getCandidate().getId());
			result.setCandidateName(a.getCandidate().getCandidateProfile().getFullName());
			result.setJobId(a.getJob().getId());
			result.setJobTitle(a.getJob().getJobTitle());
			listResult.add(result);
		}
		return listResult;
	}

	public List<AssessmentGetResDto> getAllAssessment() {
		final List<Assessment> listAssessment = assessmentDao.getAll(Assessment.class);
		final List<AssessmentGetResDto> listResult = new ArrayList<>();
		for (Assessment a : listAssessment) {
			final AssessmentGetResDto result = new AssessmentGetResDto();
			result.setAssessmentId(a.getId());
			result.setCandidateId(a.getCandidate().getId());
			result.setCandidateName(a.getCandidate().getCandidateProfile().getFullName());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setHrId(a.getHr().getId());
			result.setHrName(a.getHr().getProfile().getFullName());
			result.setSchedule(a.getSchedule().toString());
			result.setNotes(a.getNotes());
			listResult.add(result);
		}
		return listResult;
	}

	public List<MedicalCheckupGetResDto> getAllMedicalCheckup() {
		final List<MedicalCheckupGetResDto> listResult = new ArrayList<>();
		final List<MedicalCheckup> listMcu = medicalCheckupDao.getAll(MedicalCheckup.class);
		for (MedicalCheckup m : listMcu) {
			final MedicalCheckupGetResDto result = new MedicalCheckupGetResDto();
			result.setMcuId(m.getId());
			result.setCandidateId(m.getCandidate().getId());
			result.setCandidateName(m.getCandidate().getCandidateProfile().getFullName());
			if(m.getFile()!= null) {
				result.setFileId(m.getFile().getId());				
			}
			listResult.add(result);
		}
		return listResult;
	}

	public List<InterviewGetResDto> getAllInterview() {
		final List<InterviewGetResDto> listResult = new ArrayList<>();
		final List<Interview> listInterview = interviewDao.getAll(Interview.class);
		for (Interview i : listInterview) {
			final InterviewGetResDto result = new InterviewGetResDto();
			result.setInterviewId(i.getId());
			result.setCandidateId(i.getCandidate().getId());
			result.setCandidateName(i.getCandidate().getCandidateProfile().getFullName());
			result.setInterviewerId(i.getInterviewer().getId());
			result.setInterviewerName(i.getInterviewer().getProfile().getFullName());
			result.setJobId(i.getJob().getId());
			result.setJobTitle(i.getJob().getJobTitle());
			result.setSchedule(i.getSchedule().toString());
			result.setNotes(i.getNotes());
			listResult.add(result);
		}
		return listResult;
	}

	public List<HiredGetResDto> getAllHired() {
		final List<HiredGetResDto> listResult = new ArrayList<>();
		final List<Hired> listHired = hiredDao.getAll(Hired.class);
		for (Hired h : listHired) {
			final HiredGetResDto result = new HiredGetResDto();
			result.setHiredId(h.getId());
			result.setCandidateId(h.getCandidate().getId());
			result.setCandidateName(h.getCandidate().getCandidateProfile().getFullName());
			result.setJobId(h.getJob().getId());
			result.setJobTitle(h.getJob().getJobTitle());
			listResult.add(result);
		}
		return listResult;
	}

	public List<OfferingGetResDto> getAllOffering() {
		final List<OfferingGetResDto> listResult = new ArrayList<>();
		final List<Offering> listOffering = offeringDao.getAll(Offering.class);
		for (Offering o : listOffering) {
			final OfferingGetResDto result = new OfferingGetResDto();
			result.setOfferingId(o.getId());
			result.setCandidateId(o.getCandidate().getId());
			result.setCandidateName(o.getCandidate().getCandidateProfile().getFullName());
			result.setJobId(o.getJob().getId());
			result.setJobTitle(o.getJob().getJobTitle());
			listResult.add(result);
		}
		return listResult;
	}

	public InsertResDto insertApplication(ApplicationInsertReqDto data) {

		final Candidate candidate = candidateDao.getByEmail(data.getCandidateEmail());
		final Job job = jobDao.getByCode(data.getJobCode());
		final Application application = new Application();
		application.setCandidate(candidate);
		application.setJob(job);
		final Application applications = applicationDao.save(application);

		final InsertResDto result = new InsertResDto();
		result.setId(applications.getId());
		result.setMessage("Insert Application Successfully.");

		return result;
	}

	public InsertResDto insertAssessment(AssessmentInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
			final Job job = jobDao.getById(Job.class, data.getJobId());
			final User hr = userDao.getById(User.class, data.getHrId());
			final Assessment assessment = new Assessment();
			assessment.setCandidate(candidate);
			assessment.setJob(job);
			assessment.setHr(hr);
			assessment.setSchedule(DateConvert.convertDate(data.getSchedule()));
			
			final Application applicationDb = applicationDao.getByCandidateAndJob(data.getCandidateId(), data.getJobId());
			
			final Application application = applicationDao.getById(Application.class, applicationDb.getId());
			application.setIsActive(false);
			applicationDao.save(application);
			
			final Assessment assessments = assessmentDao.save(assessment);

			result.setId(assessments.getId());
			result.setMessage("Insert Assessment Successfully.");

			em().getTransaction().commit();

			final JobCandidateStatus jobCandidateStatus = jobCandidateStatusDao.getByCandidateAndJob(candidate.getEmail(),
					job.getJobCode());

			final CandidateProgressUpdateReqDto dataSend = new CandidateProgressUpdateReqDto();
			dataSend.setStatusProcessCode(StatusCodeEnum.ASSESSMENT.processCode);
			dataSend.setCandidateProgressId(jobCandidateStatus.getId());

			updateCandidateProgress(dataSend);
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		return result;
	}

	public InsertResDto insertMedicalCheckup(MedicalCheckupInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
			final Job job = jobDao.getById(Job.class, data.getJobId());
			final MedicalCheckup medicalCheckup = new MedicalCheckup();
			medicalCheckup.setCandidate(candidate);
			medicalCheckup.setJob(job);
			
			final Interview interviewDb = interviewDao.getByCandidateAndJob(data.getCandidateId(), data.getJobId());
			
			final Interview interview = interviewDao.getById(Interview.class, interviewDb.getId());
			interview.setIsActive(false);
			interviewDao.save(interview);
			
			final MedicalCheckup medicalCheckups = medicalCheckupDao.save(medicalCheckup);

			result.setId(medicalCheckups.getId());
			result.setMessage("Insert Medical Checkup Successfully.");

			em().getTransaction().commit();
			
			final JobCandidateStatus jobCandidateStatus = jobCandidateStatusDao.getByCandidateAndJob(candidate.getEmail(),
					job.getJobCode());

			final CandidateProgressUpdateReqDto dataSend = new CandidateProgressUpdateReqDto();
			dataSend.setStatusProcessCode(StatusCodeEnum.MCU.processCode);
			dataSend.setCandidateProgressId(jobCandidateStatus.getId());

			updateCandidateProgress(dataSend);
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		return result;
	}

	public InsertResDto insertInterview(InterviewInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
			final Job job = jobDao.getById(Job.class, data.getJobId());
			final User interviewer = userDao.getById(User.class, data.getInterviewerId());
			final Interview interview = new Interview();
			interview.setCandidate(candidate);
			interview.setJob(job);
			interview.setInterviewer(interviewer);
			interview.setSchedule(DateConvert.convertDate(data.getSchedule()));
			
			final Assessment assessmentDb = assessmentDao.getByCandidateAndJob(data.getCandidateId(), data.getJobId());
			
			final Assessment assessment = assessmentDao.getById(Assessment.class, assessmentDb.getId());
			assessment.setIsActive(false);
			assessmentDao.save(assessment);
			
			final Interview interviews = interviewDao.save(interview);

			result.setId(interviews.getId());
			result.setMessage("Insert Interview Successfully.");

			em().getTransaction().commit();
			
			final JobCandidateStatus jobCandidateStatus = jobCandidateStatusDao.getByCandidateAndJob(candidate.getEmail(),
					job.getJobCode());

			final CandidateProgressUpdateReqDto dataSend = new CandidateProgressUpdateReqDto();
			dataSend.setStatusProcessCode(StatusCodeEnum.INTERVIEW.processCode);
			dataSend.setCandidateProgressId(jobCandidateStatus.getId());

			updateCandidateProgress(dataSend);
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		return result;
	}

	public InsertResDto insertOffering(OfferingInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
			final Job job = jobDao.getById(Job.class, data.getJobId());
			final Offering offering = new Offering();
			offering.setCandidate(candidate);
			offering.setJob(job);
			
			final MedicalCheckup medicalCheckupDb = medicalCheckupDao.getByCandidateAndJob(data.getCandidateId(), data.getJobId());
			
			final MedicalCheckup medicalCheckup = medicalCheckupDao.getById(MedicalCheckup.class, medicalCheckupDb.getId());
			medicalCheckup.setIsActive(false);
			medicalCheckupDao.save(medicalCheckup);
			
			final Offering offerings = offeringDao.save(offering);

			result.setId(offerings.getId());
			result.setMessage("Insert Offering Successfully.");

			em().getTransaction().commit();
			
			final JobCandidateStatus jobCandidateStatus = jobCandidateStatusDao.getByCandidateAndJob(candidate.getEmail(),
					job.getJobCode());

			final CandidateProgressUpdateReqDto dataSend = new CandidateProgressUpdateReqDto();
			dataSend.setStatusProcessCode(StatusCodeEnum.OFFERING.processCode);
			dataSend.setCandidateProgressId(jobCandidateStatus.getId());

			updateCandidateProgress(dataSend);
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		return result;
	}

	public InsertResDto insertHired(HiredInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			
			final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
			final Job job = jobDao.getById(Job.class, data.getJobId());
			final Hired hired = new Hired();
			hired.setCandidate(candidate);
			hired.setJob(job);
			
			final Offering offeringDb = offeringDao.getByCandidateAndJob(data.getCandidateId(), data.getJobId());
			
			final Offering offering = offeringDao.getById(Offering.class, offeringDb.getId());
			offering.setIsActive(false);
			offeringDao.save(offering);
			
			final Hired hiredDb = hiredDao.save(hired);
			
			result.setId(hiredDb.getId());
			result.setMessage("Insert Hired Successfully.");
			
			final Employee employee = new Employee();
			employee.setCandidate(candidate);
			employee.setCompany(job.getCompany());
			
			employeeDao.save(employee);
			
			em().getTransaction().commit();
			
			final JobCandidateStatus jobCandidateStatus = jobCandidateStatusDao.getByCandidateAndJob(candidate.getEmail(),
					job.getJobCode());
			
			final CandidateProgressUpdateReqDto dataSend = new CandidateProgressUpdateReqDto();
			dataSend.setStatusProcessCode(StatusCodeEnum.HIRED.processCode);
			dataSend.setCandidateProgressId(jobCandidateStatus.getId());

			updateCandidateProgress(dataSend);
			
		}catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
		}
		
		
		return result;
	}

	public InsertResDto insertProgressStatusCandidate(CandidateProgressInsertReqDto data) {
		em().getTransaction().begin();

		final Candidate candidate = candidateDao.getByEmail(data.getCandidateEmail());
		final Job job = jobDao.getByCode(data.getJobCode());
		final StatusProcess statusProcess = statusProcessDao.getByCode(StatusCodeEnum.APPLICATION.processCode);
		final JobCandidateStatus jobCandidateStatus = new JobCandidateStatus();
		jobCandidateStatus.setCandidate(candidate);
		jobCandidateStatus.setJob(job);
		jobCandidateStatus.setStatus(statusProcess);
		final JobCandidateStatus jobCandidateStatusDb = jobCandidateStatusDao.save(jobCandidateStatus);

		final ApplicationInsertReqDto applicationInsertReqDto = new ApplicationInsertReqDto();
		applicationInsertReqDto.setCandidateEmail(data.getCandidateEmail());
		applicationInsertReqDto.setJobCode(data.getJobCode());

		insertApplication(applicationInsertReqDto);

		final InsertResDto result = new InsertResDto();
		result.setId(jobCandidateStatusDb.getId());
		result.setMessage("Insert Candidate Progress Successfully.");

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto updateNotesAssessment(AssessmentUpdateReqDto data) {
		em().getTransaction().begin();

		final Assessment assessment = assessmentDao.getById(Assessment.class, data.getAssessmentId());
		assessment.setNotes(data.getNotes());
		final Assessment assessmentResult = assessmentDao.saveAndFlush(assessment);

		final UpdateResDto result = new UpdateResDto();
		result.setVersion(assessmentResult.getVersion());
		result.setMessage("Update Assessment Notes Successfully.");

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto updateInterviewNotes(InterviewUpdateReqDto data) {
		em().getTransaction().begin();

		final Interview interview = interviewDao.getById(Interview.class, data.getInterviewId());
		interview.setNotes(data.getNotes());
		final Interview interviewResult = interviewDao.saveAndFlush(interview);

		final UpdateResDto result = new UpdateResDto();
		result.setVersion(interviewResult.getVersion());
		result.setMessage("Update Interview Notes Successfully.");

		em().getTransaction().commit();
		return result;
	}

	public UpdateResDto updateMedical(MedicalCheckupUpdateReqDto data) {
		final UpdateResDto result = new UpdateResDto();
		try {
			final MedicalCheckup medicalCheckup = medicalCheckupDao.getById(MedicalCheckup.class, data.getMedicalId());
			
			final File file = new File();
			file.setFile(data.getFile());
			file.setExt(data.getExt());
			fileDao.save(file);
			
			medicalCheckup.setFile(file);
			final MedicalCheckup medicalCheckupResult = medicalCheckupDao.save(medicalCheckup);
			
			result.setVersion(medicalCheckupResult.getVersion());
			result.setMessage("Update Medical File Successfully.");
			
		}catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return result;
	}
	
	public UpdateResDto updateCandidateProgress(CandidateProgressUpdateReqDto data) {
		final UpdateResDto result = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final JobCandidateStatus progress = jobCandidateStatusDao.getById(JobCandidateStatus.class,
					data.getCandidateProgressId());
			final StatusProcess status = statusProcessDao.getByCode(data.getStatusProcessCode());
			final StatusProcess statusResult = statusProcessDao.getById(StatusProcess.class, status.getId());
			progress.setStatus(statusResult);
			final JobCandidateStatus progressResult = jobCandidateStatusDao.saveAndFlush(progress);

			final CandidateProgressUpdateByCodeReqDto dataSend = new CandidateProgressUpdateByCodeReqDto();
			dataSend.setCandidateEmail(progress.getCandidate().getEmail());
			dataSend.setJobCode(progress.getJob().getJobCode());
			dataSend.setStatusCode(data.getStatusProcessCode());

			final String progressCandidateAPI = "http://localhost:8081/status-progress/candidate";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CandidateProgressUpdateByCodeReqDto> progressUpdate = RequestEntity
					.patch(progressCandidateAPI).headers(headers).body(dataSend);

			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(progressUpdate,
					UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				result.setVersion(progressResult.getVersion());
				result.setMessage("Update Progress Successfully.");
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
	
	public List<CandidateStageProcessResDto> getApplicationByCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		
		final List<Application> listApplication = applicationDao.getByCandidate(candidateId.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (Application a : listApplication) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setJobCode(a.getJob().getJobCode());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listApplication.size());

			listResult.add(result);
		}
		return listResult;
	}
	
	public List<CandidateStageProcessResDto> getAssessmentByCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		
		final List<Assessment> listAssessment = assessmentDao.getByCandidate(candidateId.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (Assessment a : listAssessment) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setJobCode(a.getJob().getJobCode());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listAssessment.size());

			listResult.add(result);
		}
		return listResult;
	}
	
	public List<CandidateStageProcessResDto> getInterviewByCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		
		final List<Interview> listInterview = interviewDao.getByCandidate(candidateId.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (Interview a : listInterview) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listInterview.size());

			listResult.add(result);
		}
		return listResult;
	}
	
	public List<CandidateStageProcessResDto> getHiredByCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		
		final List<Hired> listHired = hiredDao.getByCandidate(candidateId.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (Hired a : listHired) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listHired.size());

			listResult.add(result);
		}
		return listResult;
	}
	
	public List<CandidateStageProcessResDto> getOfferingByCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		
		final List<Offering> listOffering= offeringDao.getByCandidate(candidateId.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (Offering a : listOffering) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listOffering.size());

			listResult.add(result);
		}
		return listResult;
	}
	
	public List<CandidateStageProcessResDto> getMCUbyCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		
		final List<MedicalCheckup> listMedical= medicalCheckupDao.getByCandidate(candidateId.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (MedicalCheckup a : listMedical) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listMedical.size());

			listResult.add(result);
		}
		return listResult;
	}
	
	public List<CandidateStageProcessResDto> getRejectbyCandidate(String candidateEmail) {
		final Candidate candidateId = candidateDao.getByEmail(candidateEmail);
		final StatusProcess statusProcess = statusProcessDao.getByCode(StatusCodeEnum.REJECTED.processCode);
		
		final List<JobCandidateStatus> listJobCandidateStatus = jobCandidateStatusDao.getByStatus(candidateId.getId(), statusProcess.getId());
		final List<CandidateStageProcessResDto> listResult = new ArrayList<>();
		for (JobCandidateStatus a : listJobCandidateStatus) {
			final CandidateStageProcessResDto result = new CandidateStageProcessResDto();
	
			result.setApplicationId(a.getId());
			result.setJobId(a.getJob().getId());
			result.setJobName(a.getJob().getJobTitle());
			result.setStatusName(a.getJob().getJobStatus().getStatusName());
			result.setCompanyId(a.getJob().getCompany().getId());
			result.setCompanyName(a.getJob().getCompany().getCompanyName());
			result.setPhotoId(a.getJob().getCompany().getFile().getId());
			result.setCreatedAt(a.getCreatedAt().toString());
			result.setTotalStage(listJobCandidateStatus.size());

			listResult.add(result);
		}
		return listResult;
	}
}
