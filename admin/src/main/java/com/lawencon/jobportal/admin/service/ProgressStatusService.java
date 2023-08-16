package com.lawencon.jobportal.admin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.ApplicationDao;
import com.lawencon.jobportal.admin.dao.AssessmentDao;
import com.lawencon.jobportal.admin.dao.CandidateDao;
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
import com.lawencon.jobportal.admin.dto.candidateprogress.CandidateProgressUpdateReqDto;
import com.lawencon.jobportal.admin.dto.hired.HiredGetResDto;
import com.lawencon.jobportal.admin.dto.hired.HiredInsertReqDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewGetResDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewInsertReqDto;
import com.lawencon.jobportal.admin.dto.interview.InterviewUpdateReqDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupGetResDto;
import com.lawencon.jobportal.admin.dto.medicalcheckup.MedicalCheckupInsertReqDto;
import com.lawencon.jobportal.admin.dto.offering.OfferingGetResDto;
import com.lawencon.jobportal.admin.dto.offering.OfferingInsertReqDto;
import com.lawencon.jobportal.admin.dto.progress.StatusProgressGetResDto;
import com.lawencon.jobportal.admin.model.Application;
import com.lawencon.jobportal.admin.model.Assessment;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Hired;
import com.lawencon.jobportal.admin.model.Interview;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobCandidateStatus;
import com.lawencon.jobportal.admin.model.MedicalCheckup;
import com.lawencon.jobportal.admin.model.Offering;
import com.lawencon.jobportal.admin.model.StatusProcess;
import com.lawencon.jobportal.admin.model.User;

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
	private FileDao fileDao;
	
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

	public List<ApplicationGetResDto> getAllApplication() {
		final List<Application> listApplication = applicationDao.getAll(Application.class);
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
			result.setFileId(m.getFile().getId());
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
	
	public List<HiredGetResDto> getAllHired(){
		final List<HiredGetResDto> listResult = new ArrayList<>();
		final List<Hired> listHired = hiredDao.getAll(Hired.class);
		for(Hired h:listHired) {
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
	
	public List<OfferingGetResDto> getAllOffering(){
		final List<OfferingGetResDto> listResult = new ArrayList<>();
		final List<Offering> listOffering = offeringDao.getAll(Offering.class);
		for(Offering o:listOffering) {
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
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Job job = jobDao.getById(Job.class, data.getJobId());
		final Application application = new Application();
		application.setCandidate(candidate);
		application.setJob(job);
		final Application applications = applicationDao.save(application);
		
		final InsertResDto result = new InsertResDto();
		result.setId(applications.getId());
		result.setMessage("Insert Application Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public InsertResDto insertAssessment(AssessmentInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Job job = jobDao.getById(Job.class, data.getJobId());
		final User hr = userDao.getById(User.class, data.getHrId());
		final Assessment assessment = new Assessment();
		assessment.setCandidate(candidate);
		assessment.setJob(job);
		assessment.setHr(hr);
		assessment.setSchedule(LocalDateTime.parse(data.getSchedule()));
		final Assessment assessments = assessmentDao.save(assessment);
		
		final InsertResDto result = new InsertResDto();
		result.setId(assessments.getId());
		result.setMessage("Insert Assessment Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public InsertResDto insertMedicalCheckup(MedicalCheckupInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Job job = jobDao.getById(Job.class, data.getJobId());
		final File file = new File();
		file.setFile(data.getFile());
		file.setExt(file.getExt());
		final File files = fileDao.save(file);
		final MedicalCheckup medicalCheckup = new MedicalCheckup();
		medicalCheckup.setCandidate(candidate);
		medicalCheckup.setJob(job);
		medicalCheckup.setFile(files);
		final MedicalCheckup medicalCheckups = medicalCheckupDao.save(medicalCheckup);
		
		final InsertResDto result = new InsertResDto();
		result.setId(medicalCheckups.getId());
		result.setMessage("Insert Medical Checkup Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public InsertResDto insertInterview(InterviewInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Job job = jobDao.getById(Job.class, data.getJobId());
		final User interviewer = userDao.getById(User.class, data.getInterviewerId());
		final Interview interview = new Interview();
		interview.setCandidate(candidate);
		interview.setJob(job);
		interview.setInterviewer(interviewer);
		interview.setSchedule(LocalDateTime.parse(data.getSchedule()));
		final Interview interviews = interviewDao.save(interview);
		
		final InsertResDto result = new InsertResDto();
		result.setId(interviews.getId());
		result.setMessage("Insert Interview Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public InsertResDto insertOffering(OfferingInsertReqDto data){
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Job job = jobDao.getById(Job.class, data.getJobId());
		final Offering offering = new Offering();
		offering.setCandidate(candidate);
		offering.setJob(job);
		final Offering offerings = offeringDao.save(offering);
		
		final InsertResDto result = new InsertResDto();
		result.setId(offerings.getId());
		result.setMessage("Insert Offering Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public InsertResDto insertHired(HiredInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getById(Candidate.class, data.getCandidateId());
		final Job job = jobDao.getById(Job.class, data.getJobId());
		final Hired hired = new Hired();
		hired.setCandidate(candidate);
		hired.setJob(job);
		final Hired hiredDb = hiredDao.save(hired);
		
		final InsertResDto result = new InsertResDto();
		result.setId(hiredDb.getId());
		result.setMessage("Insert Hired Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public InsertResDto insertProgressStatusCandidate(CandidateProgressInsertReqDto data) {
		em().getTransaction().begin();
		
		final Candidate candidate = candidateDao.getByEmail(data.getCandidateEmail());
		final Job job = jobDao.getByCode(data.getJobCode());
		final StatusProcess statusProcess = statusProcessDao.getByCode(data.getStatusCode());
		final JobCandidateStatus jobCandidateStatus = new JobCandidateStatus();
		jobCandidateStatus.setCandidate(candidate);
		jobCandidateStatus.setJob(job);
		jobCandidateStatus.setStatus(statusProcess);
		final JobCandidateStatus jobCandidateStatusDb = jobCandidateStatusDao.save(jobCandidateStatus);
		
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
	
	
	public UpdateResDto updateCandidateProgress(CandidateProgressUpdateReqDto data) {
		em().getTransaction().begin();
		
		final JobCandidateStatus progress = jobCandidateStatusDao.getById(JobCandidateStatus.class, data.getCandidateProgressId());
		final StatusProcess status = statusProcessDao.getByCode(data.getStatusProcessCode());
		final StatusProcess statusResult = statusProcessDao.getById(StatusProcess.class, status.getId());
		progress.setStatus(statusResult);
		final JobCandidateStatus progressResult = jobCandidateStatusDao.saveAndFlush(progress);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(progressResult.getVersion());
		result.setMessage("Update Progress Successfully.");
		
		em().getTransaction().commit();
		return result;
	}
}
