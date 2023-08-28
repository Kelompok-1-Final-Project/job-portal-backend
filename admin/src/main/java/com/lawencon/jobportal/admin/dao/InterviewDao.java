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
import com.lawencon.jobportal.admin.model.Interview;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;

@Repository
public class InterviewDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<Interview> getByCandidate(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ti.id AS interview_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ti.created_at, ti.ver, tj.job_code ");
		sql.append("FROM t_interview ti ");
		sql.append("INNER JOIN t_job tj ON ti.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE ti.candidate_id = :candidateId AND ti.is_active = TRUE");

		final List<?> interviewObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();

		final List<Interview> listInterview = new ArrayList<>();
		if (interviewObjs.size() > 0) {
			for (Object interviewObj : interviewObjs) {
				final Object[] interviewArr = (Object[]) interviewObj;

				final Interview interview = new Interview();
				interview.setId(interviewArr[0].toString());

				final Job job = new Job();
				job.setId(interviewArr[1].toString());
				job.setJobTitle(interviewArr[2].toString());
				job.setJobCode(interviewArr[9].toString());

				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(interviewArr[3].toString());
				job.setJobStatus(jobStatus);

				final Company company = new Company();
				company.setId(interviewArr[4].toString());
				company.setCompanyName(interviewArr[5].toString());

				final File file = new File();
				file.setId(interviewArr[6].toString());
				company.setFile(file);
				job.setCompany(company);

				interview.setJob(job);
				interview.setCreatedAt(Timestamp.valueOf(interviewArr[7].toString()).toLocalDateTime());
				interview.setVersion(Integer.valueOf(interviewArr[8].toString()));

				listInterview.add(interview);
			}
		}

		return listInterview;
	}

	public Interview getByCandidateAndJob(String candidateId, String jobId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ti.id AS interview_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ti.created_at, ti.ver ");
		sql.append("FROM t_interview ti ");
		sql.append("INNER JOIN t_job tj ON ti.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE ti.candidate_id = :candidateId AND ta.job_id = :jobId");

		final Object interviewObj = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setParameter("jobId", jobId)
				.getSingleResult();

		final Object[] interviewArr = (Object[]) interviewObj;

		Interview interview = null;
		if (interviewArr.length > 0) {

			interview = new Interview();
			interview.setId(interviewArr[0].toString());

			final Job job = new Job();
			job.setId(interviewArr[1].toString());
			job.setJobTitle(interviewArr[2].toString());

			final JobStatus jobStatus = new JobStatus();
			jobStatus.setStatusName(interviewArr[3].toString());
			job.setJobStatus(jobStatus);

			final Company company = new Company();
			company.setId(interviewArr[4].toString());
			company.setCompanyName(interviewArr[5].toString());

			final File file = new File();
			file.setId(interviewArr[6].toString());
			company.setFile(file);
			job.setCompany(company);

			interview.setJob(job);
			interview.setCreatedAt(Timestamp.valueOf(interviewArr[7].toString()).toLocalDateTime());
			interview.setVersion(Integer.valueOf(interviewArr[8].toString()));

		}

		return interview;
	}
}
