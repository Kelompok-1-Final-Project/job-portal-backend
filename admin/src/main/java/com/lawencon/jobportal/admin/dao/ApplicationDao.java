package com.lawencon.jobportal.admin.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Application;
import com.lawencon.jobportal.admin.model.City;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.EmploymentType;
import com.lawencon.jobportal.admin.model.Industry;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobCandidateStatus;
import com.lawencon.jobportal.admin.model.JobPosition;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.util.DateConvert;

@Repository
public class ApplicationDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Application> getByCandidate(String candidateId) {
		final String sql = "SELECT "
				+ "	ta.id AS application_id, "
				+ "	tj.id AS job_id, "
				+ "	tj.job_title, "
				+ "	tjs.status_name, "
				+ "	tc.id AS company_id, "
				+ "	tc.company_name, "
				+ "	ta.created_at, "
				+ "	ta.ver "
				+ "FROM "
				+ "	t_application ta "
				+ "INNER JOIN "
				+ "	t_job tj ON ta.job_id = tj.id "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id "
				+ "WHERE "
				+ "	ta.candidate_id = :candidateId ";
		
		final List<?> applicationObjs = this.em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		final List<Application> listApplication = new ArrayList<>();
		if(applicationObjs.size() > 0) {
			for(Object applicationObj : applicationObjs) {
				final Object[] applicationArr = (Object[]) applicationObj;
				
				final Application application = new Application();
				application.setId(applicationArr[0].toString());
				
				final Job job = new Job();
				job.setId(applicationArr[1].toString());
				job.setJobTitle(applicationArr[2].toString());
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(applicationArr[3].toString());
				job.setJobStatus(jobStatus);
				
				final Company company = new Company();
				company.setId(applicationArr[4].toString());
				company.setCompanyName(applicationArr[5].toString());
				job.setCompany(company);
				
				application.setJob(job);
				application.setCreatedAt(Timestamp.valueOf(applicationArr[6].toString()).toLocalDateTime());
				application.setVersion(Integer.valueOf(applicationArr[7].toString()));
				
				listApplication.add(application);
			}
		}
		
		return listApplication;
	}
}
