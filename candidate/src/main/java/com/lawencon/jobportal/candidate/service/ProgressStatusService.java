package com.lawencon.jobportal.candidate.service;

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
import com.lawencon.jobportal.candidate.dao.JobCandidateStatusDao;
import com.lawencon.jobportal.candidate.dao.JobDao;
import com.lawencon.jobportal.candidate.dao.StatusProcessDao;
import com.lawencon.jobportal.candidate.dao.UserDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.jobstatus.CandidateProgressInsertReqDto;
import com.lawencon.jobportal.candidate.dto.jobstatus.CandidateProgressUpdateReqDto;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.JobCandidateStatus;
import com.lawencon.jobportal.candidate.model.StatusProcess;
import com.lawencon.jobportal.candidate.model.User;

@Service
public class ProgressStatusService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private StatusProcessDao statusProcessDao;
	
	@Autowired
	private JobCandidateStatusDao jobCandidateStatusDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public InsertResDto insertProgressStatusCandidate(CandidateProgressInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			
			final User candidate = userDao.getByEmail(data.getCandidateEmail());
			final Job job = jobDao.getByCode(data.getJobCode());
			final StatusProcess statusProcess = statusProcessDao.getByCode(data.getStatusCode());
			final JobCandidateStatus jobCandidateStatus = new JobCandidateStatus();
			jobCandidateStatus.setCandidate(candidate);
			jobCandidateStatus.setJob(job);
			jobCandidateStatus.setStatus(statusProcess);

			final JobCandidateStatus statusResult = jobCandidateStatusDao.save(jobCandidateStatus);
			
			final String progressAdminAPI = "http://localhost:8080/status-progress/candidate";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CandidateProgressInsertReqDto> progressInsert = RequestEntity.post(progressAdminAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(progressInsert, InsertResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(statusResult.getId());
				result.setMessage("Insert Application Successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UpdateResDto updateCandidateProgress(CandidateProgressUpdateReqDto data) {
		em().getTransaction().begin();
		
		final JobCandidateStatus progress = jobCandidateStatusDao.getByCandidateAndJob(data.getCandidateEmail(), data.getJobCode());
		final JobCandidateStatus getId = jobCandidateStatusDao.getById(JobCandidateStatus.class, progress.getId());
		final StatusProcess status = statusProcessDao.getByCode(data.getStatusCode());
		getId.setStatus(status);
		final JobCandidateStatus progressResult = jobCandidateStatusDao.saveAndFlush(getId);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(progressResult.getVersion());
		result.setMessage("Update Progress Successfully.");
		
		em().getTransaction().commit();
		return result;
	}

}
