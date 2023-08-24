package com.lawencon.jobportal.admin.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Assessment;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;

@Repository
public class AssessmentDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Assessment> getByCandidate(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ta.id AS assessment_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ta.created_at, ta.ver ");
		sql.append("FROM t_assessment ta ");
		sql.append("INNER JOIN t_job tj ON ta.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE ta.candidate_id = :candidateId ");
		
		final List<?> assessmentObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();
		final List<Assessment> listAssessments = new ArrayList<>();
		if(assessmentObjs.size() > 0) {
			for(Object assessmentObj : assessmentObjs) {
				final Object[] assessmentArr = (Object[]) assessmentObj;
				
				final Assessment assessment = new Assessment();
				assessment.setId(assessmentArr[0].toString());
				
				final Job job = new Job();
				job.setId(assessmentArr[1].toString());
				job.setJobTitle(assessmentArr[2].toString());
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(assessmentArr[3].toString());
				job.setJobStatus(jobStatus);
				
				final Company company = new Company();
				company.setId(assessmentArr[4].toString());
				company.setCompanyName(assessmentArr[5].toString());

				final File file = new File();
				file.setId(assessmentArr[6].toString());
				company.setFile(file);
				job.setCompany(company);
				
				assessment.setJob(job);
				assessment.setCreatedAt(Timestamp.valueOf(assessmentArr[7].toString()).toLocalDateTime());
				assessment.setVersion(Integer.valueOf(assessmentArr[8].toString()));
				
				listAssessments.add(assessment);
			}
		}
		
		return listAssessments;
	}
}
