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
import com.lawencon.jobportal.admin.model.MedicalCheckup;

@Repository
public class MedicalCheckupDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<MedicalCheckup> getByUser(String userId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tmc.id AS mcu_id, tj.id AS job_id, tj.job_title, ");
		sql.append("tc.id AS candidate_id, tcp.full_name AS candidate_name, tf.id AS file_id ");
		sql.append("FROM t_medical_checkup tmc ");
		sql.append("INNER JOIN t_job tj ON tmc.job_id = tj.id ");
		sql.append("INNER JOIN t_candidate tc ON tmc.candidate_id = tc.id ");
		sql.append("INNER JOIN t_candidate_profile tcp ON tc.profile_id = tcp.id ");
		sql.append("LEFT JOIN t_file tf ON tmc.file_id = tf.id ");
		sql.append("WHERE tj.hr_id = :userId OR tj.interviewer_id = :userId ");

		final List<?> medicalObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
				.getResultList();
		final List<MedicalCheckup> listMedical = new ArrayList<>();
		if (medicalObjs.size() > 0) {
			for (Object medicalObj : medicalObjs) {
				final Object[] medicalArr = (Object[]) medicalObj;

				final MedicalCheckup medicalCheckup = new MedicalCheckup();
				medicalCheckup.setId(medicalArr[0].toString());

				final Job job = new Job();
				job.setId(medicalArr[1].toString());
				job.setJobTitle(medicalArr[2].toString());
				medicalCheckup.setJob(job);
				
				final Candidate candidate = new Candidate();
				candidate.setId(medicalArr[3].toString());
				
				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(medicalArr[4].toString());
				candidate.setCandidateProfile(candidateProfile);
				medicalCheckup.setCandidate(candidate);
				
				if (medicalArr[5] != null) {
					final File file = new File();
					file.setId(medicalArr[5].toString());
					medicalCheckup.setFile(file);
				}

				listMedical.add(medicalCheckup);
			}
		}
		
		return listMedical;
	}
	
	public List<MedicalCheckup> getByCandidate(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tmc.id AS mcu_id, tj.id AS job_id, tj.job_title, tjs.status_name,  ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, tmc.created_at, tmc.ver, tj.job_code ");
		sql.append("FROM t_medical_checkup tmc ");
		sql.append("INNER JOIN t_job tj ON tmc.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE tmc.candidate_id = :candidateId AND tmc.is_active = TRUE ");

		final List<?> medicalObjs = this.em().createNativeQuery(sql.toString()).setParameter("candidateId", candidateId)
				.getResultList();
		final List<MedicalCheckup> listMedical = new ArrayList<>();
		if (medicalObjs.size() > 0) {
			for (Object medicalObj : medicalObjs) {
				final Object[] medicalArr = (Object[]) medicalObj;

				final MedicalCheckup medicalCheckup = new MedicalCheckup();
				medicalCheckup.setId(medicalArr[0].toString());

				final Job job = new Job();
				job.setId(medicalArr[1].toString());
				job.setJobTitle(medicalArr[2].toString());
				job.setJobCode(medicalArr[9].toString());

				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(medicalArr[3].toString());
				job.setJobStatus(jobStatus);

				final Company company = new Company();
				company.setId(medicalArr[4].toString());
				company.setCompanyName(medicalArr[5].toString());

				final File file = new File();
				file.setId(medicalArr[6].toString());
				company.setFile(file);
				job.setCompany(company);

				medicalCheckup.setJob(job);
				medicalCheckup.setCreatedAt(Timestamp.valueOf(medicalArr[7].toString()).toLocalDateTime());
				medicalCheckup.setVersion(Integer.valueOf(medicalArr[8].toString()));

				listMedical.add(medicalCheckup);
			}
		}

		return listMedical;
	}

	public MedicalCheckup getByCandidateAndJob(String candidateId, String jobId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tmc.id AS mcu_id, tj.id AS job_id, tj.job_title, tjs.status_name,  ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, tmc.created_at, tmc.ver ");
		sql.append("FROM t_medical_checkup tmc ");
		sql.append("INNER JOIN t_job tj ON tmc.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("WHERE tmc.candidate_id = :candidateId AND tmc.job_id = :jobId");

		final Object medicalObj = this.em().createNativeQuery(sql.toString()).setParameter("candidateId", candidateId)
				.setParameter("jobId", jobId).getSingleResult();

		final Object[] medicalArr = (Object[]) medicalObj;

		MedicalCheckup medicalCheckup = null;
		
		if (medicalArr.length > 0) {
			medicalCheckup = new MedicalCheckup();
			medicalCheckup.setId(medicalArr[0].toString());

			final Job job = new Job();
			job.setId(medicalArr[1].toString());
			job.setJobTitle(medicalArr[2].toString());

			final JobStatus jobStatus = new JobStatus();
			jobStatus.setStatusName(medicalArr[3].toString());
			job.setJobStatus(jobStatus);

			final Company company = new Company();
			company.setId(medicalArr[4].toString());
			company.setCompanyName(medicalArr[5].toString());

			final File file = new File();
			file.setId(medicalArr[6].toString());
			company.setFile(file);
			job.setCompany(company);

			medicalCheckup.setJob(job);
			medicalCheckup.setCreatedAt(Timestamp.valueOf(medicalArr[7].toString()).toLocalDateTime());
			medicalCheckup.setVersion(Integer.valueOf(medicalArr[8].toString()));
		}

		return medicalCheckup;
	}
}
