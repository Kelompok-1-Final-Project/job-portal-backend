package com.lawencon.jobportal.admin.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Interview;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.Profile;
import com.lawencon.jobportal.admin.model.User;

@Repository
public class InterviewDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<Interview> getByUser(String userId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ti.id AS interview_id, tj.id AS job_id, tj.job_title, ti.interviewer_id, tp.full_name,  ");
		sql.append("ti.schedule , ti.notes, tc.id AS candidate_id, tcp.full_name AS candidate_name ");
		sql.append("FROM t_interview ti ");
		sql.append("INNER JOIN t_job tj ON ti.job_id = tj.id ");
		sql.append("INNER JOIN t_user tu ON ti.interviewer_id = tu.id ");
		sql.append("INNER JOIN t_profile tp ON tu.profile_id = tp.id ");
		sql.append("INNER JOIN t_candidate tc ON ti.candidate_id = tc.id ");
		sql.append("INNER JOIN t_candidate_profile tcp ON tc.profile_id = tcp.id ");
		sql.append("WHERE tj.hr_id = :userId OR tj.interviewer_id = :userId");

		final List<?> interviewObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
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
				interview.setJob(job);
				
				final User user = new User();
				user.setId(interviewArr[3].toString());
				
				final Profile profile = new Profile();
				profile.setFullName(interviewArr[4].toString());
				user.setProfile(profile);
				
				interview.setSchedule(Timestamp.valueOf(interviewArr[5].toString()).toLocalDateTime());
				interview.setNotes(interviewArr[6].toString());
				
				final Candidate candidate = new Candidate();
				candidate.setId(interviewArr[7].toString());
				
				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(interviewArr[8].toString());
				candidate.setCandidateProfile(candidateProfile);
				
				listInterview.add(interview);
			}
		}

		return listInterview;
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
