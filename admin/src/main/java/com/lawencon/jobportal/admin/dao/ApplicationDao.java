package com.lawencon.jobportal.admin.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Application;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;

@Repository
public class ApplicationDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<Application> getByUser(String userId){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ta.id AS application_id, tj.id AS job_id, tj.job_title, tc.id AS candidate_id, tcp.full_name  ");
		sql.append("FROM t_application ta ");
		sql.append("INNER JOIN t_job tj ON ta.job_id = tj.id ");
		sql.append("INNER JOIN t_candidate tc ON ta.candidate_id = tc.id ");
		sql.append("INNER JOIN t_candidate_profile tcp ON tc.profile_id = tcp.id ");
		sql.append("WHERE tj.hr_id = :userId OR tj.interviewer_id = :userId ");
		
		final List<?> applicationObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
				.getResultList();
		
		final List<Application> listApplication = new ArrayList<>();
		
		if (applicationObjs.size() > 0) {
			for (Object applicationObj : applicationObjs) {
				final Object[] applicationArr = (Object[]) applicationObj;

				final Application application = new Application();
				application.setId(applicationArr[0].toString());

				final Job job = new Job();
				job.setId(applicationArr[1].toString());
				job.setJobTitle(applicationArr[2].toString());
				application.setJob(job);
				
				final Candidate candidate = new Candidate();
				candidate.setId(applicationArr[3].toString());

				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(applicationArr[4].toString());
				candidate.setCandidateProfile(candidateProfile);
				application.setCandidate(candidate);	

				listApplication.add(application);
			}
		}
		
		return listApplication;
	}
	
	public List<Application> getByCandidate(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ta.id AS application_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ta.created_at, ta.ver, tj.job_code ");
		sql.append("FROM t_application ta ");
		sql.append("INNER JOIN t_job tj ON ta.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE ta.candidate_id = :candidateId AND ta.is_active = TRUE ");

		final List<?> applicationObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		final List<Application> listApplication = new ArrayList<>();
		
		if (applicationObjs.size() > 0) {
			for (Object applicationObj : applicationObjs) {
				final Object[] applicationArr = (Object[]) applicationObj;

				final Application application = new Application();
				application.setId(applicationArr[0].toString());

				final Job job = new Job();
				job.setId(applicationArr[1].toString());
				job.setJobTitle(applicationArr[2].toString());
				job.setJobCode(applicationArr[9].toString());

				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(applicationArr[3].toString());
				job.setJobStatus(jobStatus);

				final Company company = new Company();
				company.setId(applicationArr[4].toString());
				company.setCompanyName(applicationArr[5].toString());

				final File file = new File();
				file.setId(applicationArr[6].toString());
				company.setFile(file);
				job.setCompany(company);

				application.setJob(job);
				application.setCreatedAt(Timestamp.valueOf(applicationArr[7].toString()).toLocalDateTime());
				application.setVersion(Integer.valueOf(applicationArr[8].toString()));

				listApplication.add(application);
			}
		}

		return listApplication;
	}

	public Application getByCandidateAndJob(String candidateId, String jobId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ta.id AS application_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ta.created_at, ta.ver ");
		sql.append("FROM t_application ta ");
		sql.append("INNER JOIN t_job tj ON ta.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE ta.candidate_id = :candidateId  AND ta.job_id = :jobId ");

		final Object applicationObj = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setParameter("jobId", jobId)
				.getSingleResult();


		final Object[] applicationArr = (Object[]) applicationObj;

		Application application = null;
		
		if (applicationArr.length > 0) {
			application = new Application();
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

			final File file = new File();
			file.setId(applicationArr[6].toString());
			company.setFile(file);
			job.setCompany(company);

			application.setJob(job);
			application.setCreatedAt(Timestamp.valueOf(applicationArr[7].toString()).toLocalDateTime());
			application.setVersion(Integer.valueOf(applicationArr[8].toString()));

		}
		
		return application;
	}
}
