package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.JobCandidateStatus;

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
	
	public JobCandidateStatus getByCandidateAndCompany(String candidateEmail, String companyCode) {
		final String sql = "SELECT "
				+ "jcs "
				+ "FROM "
				+ "JobCandidateStatus jcs "
				+ "WHERE "
				+ "jcs.job.company.companyCode = :companyCode AND jcs.candidate.email = :candidateEmail ";
		
		final JobCandidateStatus jobCandidateStatus = em().createQuery(sql, JobCandidateStatus.class)
				.setParameter("companyCode", companyCode)
				.setParameter("candidateEmail", candidateEmail)
				.getSingleResult();
		
		return jobCandidateStatus;
	}
}
