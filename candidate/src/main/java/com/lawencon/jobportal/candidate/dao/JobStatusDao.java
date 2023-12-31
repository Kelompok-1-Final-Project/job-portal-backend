package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.JobStatus;

@Repository
public class JobStatusDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public JobStatus getByCode(String statusCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, status_code, status_name, ver ");
		sql.append("FROM t_job_status ");
		sql.append("WHERE status_code = :statusCode ");
		
		final Object jobStatusObj = em().createNativeQuery(sql.toString())
				.setParameter("statusCode", statusCode)
				.getSingleResult();

		final Object[] jobStatusArr = (Object[]) jobStatusObj;

		JobStatus jobStatus = null;

		if (jobStatusArr.length > 0) {
			jobStatus = new JobStatus();
			jobStatus.setId(jobStatusArr[0].toString());
			jobStatus.setStatusCode(jobStatusArr[1].toString());
			jobStatus.setStatusName(jobStatusArr[2].toString());
			jobStatus.setVersion(Integer.valueOf(jobStatusArr[3].toString()));
		}

		return jobStatus;
	}
}
