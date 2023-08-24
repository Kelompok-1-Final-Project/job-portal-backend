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
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.Offering;

@Repository
public class OfferingDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Offering> getByCandidate(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT to.id AS offering_id, tj.id AS job_id, tj.job_title, tjs.status_name, tc.id AS company_id, ");
		sql.append("tc.company_name, tc.file_id, to.created_at, to.ver ");
		sql.append("FROM t_offering to ");
		sql.append("INNER JOIN t_job tj ON to.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE to.candidate_id = :candidateId ");

		final List<?> offeringObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();
		final List<Offering> listOffering = new ArrayList<>();
		if(offeringObjs.size() > 0) {
			for(Object offeringObj : offeringObjs) {
				final Object[] offeringArr = (Object[]) offeringObj;
				
				final Offering offering = new Offering();
				offering.setId(offeringArr[0].toString());
				
				final Job job = new Job();
				job.setId(offeringArr[1].toString());
				job.setJobTitle(offeringArr[2].toString());
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(offeringArr[3].toString());
				job.setJobStatus(jobStatus);
				
				final Company company = new Company();
				company.setId(offeringArr[4].toString());
				company.setCompanyName(offeringArr[5].toString());
				
				final File file = new File();
				file.setId(offeringArr[6].toString());
				company.setFile(file);
				job.setCompany(company);
				
				offering.setJob(job);
				offering.setCreatedAt(Timestamp.valueOf(offeringArr[7].toString()).toLocalDateTime());
				offering.setVersion(Integer.valueOf(offeringArr[8].toString()));
				
				listOffering.add(offering);
			}
		}
		
		return listOffering;
	}
}
