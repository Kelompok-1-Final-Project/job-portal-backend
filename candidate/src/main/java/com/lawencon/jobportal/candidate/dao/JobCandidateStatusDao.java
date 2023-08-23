package com.lawencon.jobportal.candidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.JobCandidateStatus;
import com.lawencon.jobportal.candidate.model.StatusProcess;
import com.lawencon.jobportal.candidate.model.User;

@Repository
public class JobCandidateStatusDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public JobCandidateStatus getByCandidateAndJob(String candidateEmail, String jobCode) {
		final String sql = "SELECT "
				+ "jcs "
				+ "FROM "
				+ "JobCandidateStatus jcs "
				+ "WHERE "
				+ "jcs.job.jobCode = :jobCode AND jcs.candidate.email = :candidateEmail ";
		
		final JobCandidateStatus jobCandidateStatus = em().createQuery(sql, JobCandidateStatus.class)
				.setParameter("jobCode", jobCode)
				.setParameter("candidateEmail", candidateEmail)
				.getSingleResult();
		
		return jobCandidateStatus;
	}
	
	public List<JobCandidateStatus> getByCandidate(String candidateId) {
		final String sql = "SELECT  "
				+ "id, candidate_id, job_id, status_id  "
				+ "FROM  "
				+ "t_job_candidate_status tjcs  "
				+ "WHERE  "
				+ "tjcs.candidate_id = :candidateId ";
		
		final List<?> jobCandidateStatusObjs = em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		final List<JobCandidateStatus> result = new ArrayList<>();
		
		if(jobCandidateStatusObjs.size() > 0) {
			for (Object jobCandidateStatusObj : jobCandidateStatusObjs) {
				final Object[] jobCandidateStatusArr = (Object[]) jobCandidateStatusObj;
				
				final JobCandidateStatus jobCandidateStatus = new JobCandidateStatus();
				jobCandidateStatus.setId(jobCandidateStatusArr[0].toString());
				
				final User candidate = new User();
				candidate.setId(jobCandidateStatusArr[1].toString());
				jobCandidateStatus.setCandidate(candidate);
				
				final Job job = new Job();
				job.setId(jobCandidateStatusArr[2].toString());
				jobCandidateStatus.setJob(job);
				
				final StatusProcess statusProcess = new StatusProcess();
				statusProcess.setId(jobCandidateStatusArr[3].toString());
				jobCandidateStatus.setStatus(statusProcess);
				
				result.add(jobCandidateStatus);
			}
		}
		
		return result;
	}
}
