package com.lawencon.jobportal.admin.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Hired;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;

@Repository
public class HiredDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Hired> getByCandidate(String candidateId) {
		final String sql = "SELECT "
				+ "	th.id AS hired_id, "
				+ "	tj.id AS job_id, "
				+ "	tj.job_title, "
				+ "	tjs.status_name, "
				+ "	tc.id AS company_id, "
				+ "	tc.company_name, "
				+ " tc.file_id, "
				+ "	th.created_at, "
				+ "	th.ver "
				+ "FROM "
				+ "	t_hired th "
				+ "INNER JOIN "
				+ "	t_job tj ON th.job_id = tj.id "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id "
				+ "WHERE "
				+ "	th.candidate_id = :candidateId ";
		
		final List<?> hiredObjs = this.em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		final List<Hired> listHired = new ArrayList<>();
		if(hiredObjs.size() > 0) {
			for(Object hiredObj : hiredObjs) {
				final Object[] hiredArr = (Object[]) hiredObj;
				
				final Hired hired = new Hired();
				hired.setId(hiredArr[0].toString());
				
				final Job job = new Job();
				job.setId(hiredArr[1].toString());
				job.setJobTitle(hiredArr[2].toString());
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(hiredArr[3].toString());
				job.setJobStatus(jobStatus);
				
				final Company company = new Company();
				company.setId(hiredArr[4].toString());
				company.setCompanyName(hiredArr[5].toString());

				final File file = new File();
				file.setId(hiredArr[6].toString());
				company.setFile(file);
				job.setCompany(company);
				
				hired.setJob(job);
				hired.setCreatedAt(Timestamp.valueOf(hiredArr[7].toString()).toLocalDateTime());
				hired.setVersion(Integer.valueOf(hiredArr[8].toString()));
				
				listHired.add(hired);
			}
		}
		
		return listHired;
	}
}
