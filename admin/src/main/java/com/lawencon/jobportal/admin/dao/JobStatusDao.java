package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.JobStatus;

@Repository
public class JobStatusDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public JobStatus getByCode(String statusCode) {
		final String sql = "SELECT "
				+ "id, status_code, status_name, ver "
				+ "FROM "
				+ "t_job_status "
				+ "WHERE "
				+ "status_code = :statusCode ";

		final Object jobStatusObj = em().createNativeQuery(sql)
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
