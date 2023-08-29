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
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.Offering;

@Repository
public class OfferingDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Offering> getByUser(String userId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tof.id AS offering_id, tj.id AS job_id, tj.job_title, tc.id AS candidate_id, tcp.full_name  ");
		sql.append("FROM t_offering tof ");
		sql.append("INNER JOIN t_job tj ON tof.job_id = tj.id ");
		sql.append("INNER JOIN t_candidate tc ON tof.candidate_id = tc.id ");
		sql.append("INNER JOIN t_candidate_profile tcp ON tc.profile_id = tcp.id ");
		sql.append("WHERE tj.hr_id = :userId OR tj.interviewer_id = :userId ");

		final List<?> offeringObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
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
				offering.setJob(job);
				
				final Candidate candidate = new Candidate();
				candidate.setId(offeringArr[3].toString());

				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(offeringArr[4].toString());
				candidate.setCandidateProfile(candidateProfile);
				offering.setCandidate(candidate);	
				
				listOffering.add(offering);
			}
		}
		
		return listOffering;
	}
	
	public List<Offering> getByCandidate(String candidateId, Integer startIndex, Integer endIndex) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tof.id AS offering_id, tj.id AS job_id, tj.job_title, tjs.status_name, tc.id AS company_id, ");
		sql.append("tc.company_name, tc.file_id, tof.created_at, tof.ver, tj.job_code ");
		sql.append("FROM t_offering tof ");
		sql.append("INNER JOIN t_job tj ON tof.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE tof.candidate_id = :candidateId AND tof.is_active = TRUE ");

		final List<?> offeringObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setFirstResult(startIndex)
				.setMaxResults(endIndex)
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
				job.setJobCode(offeringArr[9].toString());
				
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
	
	public List<Offering> getByCandidateNonPagination(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tof.id AS offering_id, tj.id AS job_id, tj.job_title, tjs.status_name, tc.id AS company_id, ");
		sql.append("tc.company_name, tc.file_id, tof.created_at, tof.ver, tj.job_code ");
		sql.append("FROM t_offering tof ");
		sql.append("INNER JOIN t_job tj ON tof.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE tof.candidate_id = :candidateId AND tof.is_active = TRUE ");

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
				job.setJobCode(offeringArr[9].toString());
				
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
	
	public Offering getByCandidateAndJob(String candidateId, String jobId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tof.id AS offering_id, tj.id AS job_id, tj.job_title, tjs.status_name, tc.id AS company_id, ");
		sql.append("tc.company_name, tc.file_id, tof.created_at, tof.ver ");
		sql.append("FROM t_offering tof ");
		sql.append("INNER JOIN t_job tj ON tof.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE tof.candidate_id = :candidateId AND tof.job_id = :jobId ");

		final Object offeringObj = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setParameter("jobId", jobId)
				.getSingleResult();
		
		final Object[] offeringArr = (Object[]) offeringObj;

		Offering offering = null;
		
		if (offeringArr.length > 0) {

			offering = new Offering();
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

		}

		return offering;
	}
}
