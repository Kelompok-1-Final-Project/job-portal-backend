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
import com.lawencon.jobportal.admin.model.JobCandidateStatus;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.StatusProcess;

@Repository
public class JobCandidateStatusDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<JobCandidateStatus> getByStatus(String candidateId, String statusId, Integer startIndex, Integer endIndex) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tjcs.id AS status_job_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, tjcs.created_at, tsp.id AS status_stage_id, ");
		sql.append("tsp.process_name, tjcs.ver, tj.job_code ");
		sql.append("FROM t_job_candidate_status tjcs ");
		sql.append("INNER JOIN t_job tj ON tjcs.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_status_process tsp ON tjcs.status_id = tsp.id ");
		sql.append("WHERE tjcs.status_id = :statusId AND tjcs.candidate_id = :candidateId ");
		
		final List<?> jobStatusObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setParameter("statusId", statusId)
				.setFirstResult(startIndex)
				.setMaxResults(endIndex)
				.getResultList();
		final List<JobCandidateStatus> listJobStatus = new ArrayList<>();
		if(jobStatusObjs.size() > 0) {
			for(Object jobStatusObj : jobStatusObjs) {
				final Object[] jobStatusArr = (Object[]) jobStatusObj;
				
				final JobCandidateStatus jobCandidateStatus = new JobCandidateStatus();
				jobCandidateStatus.setId(jobStatusArr[0].toString());
				
				final Job job = new Job();
				job.setId(jobStatusArr[1].toString());
				job.setJobTitle(jobStatusArr[2].toString());
				job.setJobCode(jobStatusArr[11].toString());
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobStatusArr[3].toString());
				job.setJobStatus(jobStatus);
				
				final Company company = new Company();
				company.setId(jobStatusArr[4].toString());
				company.setCompanyName(jobStatusArr[5].toString());
				
				final File file = new File();
				file.setId(jobStatusArr[6].toString());
				company.setFile(file);
				job.setCompany(company);
				
				jobCandidateStatus.setJob(job);
				jobCandidateStatus.setCreatedAt(Timestamp.valueOf(jobStatusArr[7].toString()).toLocalDateTime());
				
				final StatusProcess statusProcess = new StatusProcess();
				statusProcess.setId(jobStatusArr[8].toString());
				statusProcess.setProcessName(jobStatusArr[9].toString());
				jobCandidateStatus.setStatus(statusProcess);
				
				jobCandidateStatus.setVersion(Integer.valueOf(jobStatusArr[10].toString()));
				
				listJobStatus.add(jobCandidateStatus);
			}
		}
		
		return listJobStatus;
	}
	
	public List<JobCandidateStatus> getByStatusNonPagination(String candidateId, String statusId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tjcs.id AS status_job_id, tj.id AS job_id, tj.job_title, tjs.status_name, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, tjcs.created_at, tsp.id AS status_stage_id, ");
		sql.append("tsp.process_name, tjcs.ver, tj.job_code ");
		sql.append("FROM t_job_candidate_status tjcs ");
		sql.append("INNER JOIN t_job tj ON tjcs.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_status_process tsp ON tjcs.status_id = tsp.id ");
		sql.append("WHERE tjcs.status_id = :statusId AND tjcs.candidate_id = :candidateId ");
		
		final List<?> jobStatusObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setParameter("statusId", statusId)
				.getResultList();
		final List<JobCandidateStatus> listJobStatus = new ArrayList<>();
		if(jobStatusObjs.size() > 0) {
			for(Object jobStatusObj : jobStatusObjs) {
				final Object[] jobStatusArr = (Object[]) jobStatusObj;
				
				final JobCandidateStatus jobCandidateStatus = new JobCandidateStatus();
				jobCandidateStatus.setId(jobStatusArr[0].toString());
				
				final Job job = new Job();
				job.setId(jobStatusArr[1].toString());
				job.setJobTitle(jobStatusArr[2].toString());
				job.setJobCode(jobStatusArr[11].toString());
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobStatusArr[3].toString());
				job.setJobStatus(jobStatus);
				
				final Company company = new Company();
				company.setId(jobStatusArr[4].toString());
				company.setCompanyName(jobStatusArr[5].toString());
				
				final File file = new File();
				file.setId(jobStatusArr[6].toString());
				company.setFile(file);
				job.setCompany(company);
				
				jobCandidateStatus.setJob(job);
				jobCandidateStatus.setCreatedAt(Timestamp.valueOf(jobStatusArr[7].toString()).toLocalDateTime());
				
				final StatusProcess statusProcess = new StatusProcess();
				statusProcess.setId(jobStatusArr[8].toString());
				statusProcess.setProcessName(jobStatusArr[9].toString());
				jobCandidateStatus.setStatus(statusProcess);
				
				jobCandidateStatus.setVersion(Integer.valueOf(jobStatusArr[10].toString()));
				
				listJobStatus.add(jobCandidateStatus);
			}
		}
		
		return listJobStatus;
	}
	
	public List<JobCandidateStatus> getByJob(String jobCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT jcs FROM JobCandidateStatus jcs WHERE jcs.job.jobCode = :jobCode");
		
		final List<JobCandidateStatus> jobCandidateStatus = em().createQuery(sql.toString(), JobCandidateStatus.class)
				.setParameter("jobCode", jobCode)
				.getResultList();
		
		return jobCandidateStatus;
	}
	
	public JobCandidateStatus getByCandidateAndJob(String candidateEmail, String jobCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT jcs FROM JobCandidateStatus jcs ");
		sql.append("WHERE jcs.job.jobCode = :jobCode AND jcs.candidate.email = :candidateEmail ");
		
		final JobCandidateStatus jobCandidateStatus = em().createQuery(sql.toString(), JobCandidateStatus.class)
				.setParameter("jobCode", jobCode)
				.setParameter("candidateEmail", candidateEmail)
				.getSingleResult();
		
		return jobCandidateStatus;
	}
	
	public JobCandidateStatus getByCandidateAndCompany(String candidateEmail, String companyCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT jcs FROM JobCandidateStatus jcs ");
		sql.append("WHERE jcs.job.company.companyCode = :companyCode AND jcs.candidate.email = :candidateEmail ");
		
		final JobCandidateStatus jobCandidateStatus = em().createQuery(sql.toString(), JobCandidateStatus.class)
				.setParameter("companyCode", companyCode)
				.setParameter("candidateEmail", candidateEmail)
				.getSingleResult();
		
		return jobCandidateStatus;
	}
}
