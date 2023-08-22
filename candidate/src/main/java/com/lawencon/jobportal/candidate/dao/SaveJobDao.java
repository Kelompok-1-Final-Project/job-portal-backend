package com.lawencon.jobportal.candidate.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Company;
import com.lawencon.jobportal.candidate.model.EmploymentType;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.JobPosition;
import com.lawencon.jobportal.candidate.model.JobStatus;
import com.lawencon.jobportal.candidate.model.SaveJob;

@Repository
public class SaveJobDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<SaveJob> getByCandidate(String candidateId) {
		final String sql = "SELECT  "
				+ "	tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, tc.company_name, tjp.position_name, tjs.status_name, tet.employment_name, sj.ver, sj.id AS saved_job_id  "
				+ "FROM  "
				+ " t_save_job sj  "
				+ "INNER JOIN  "
				+ "	t_job tj ON tj.id = sj.job_id  "
				+ "INNER JOIN   "
				+ "	t_company tc ON tc.id = tj.company_id   "
				+ "INNER JOIN   "
				+ "	t_city tci ON tci.id = tc.city_id   "
				+ "INNER JOIN   "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id   "
				+ "INNER JOIN   "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id   "
				+ "INNER JOIN   "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id "
				+ "WHERE  "
				+ "	sj.candidate_id = :candidateId ";
		
		final List<?> saveJobsObj = em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		final List<SaveJob> listSaveJob = new ArrayList<>();
		
		if(saveJobsObj.size() > 0) {
			for (Object saveJobObj : saveJobsObj) {
				final Object[] saveJobArr = (Object[]) saveJobObj;
				
				final Job job = new Job();
				job.setId(saveJobArr[0].toString());
				job.setJobTitle(saveJobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(saveJobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(saveJobArr[3].toString()));
				job.setDescription(saveJobArr[4].toString());
				job.setEndDate(LocalDate.parse(saveJobArr[5].toString()));
				
				final Company company = new Company();
				company.setCompanyName(saveJobArr[6].toString());
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(saveJobArr[7].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(saveJobArr[8].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(saveJobArr[9].toString());
				job.setEmployementType(employmentType);
				
				final SaveJob saveJob = new SaveJob();
				saveJob.setJob(job);
				saveJob.setVersion(Integer.valueOf(saveJobArr[10].toString()));
				saveJob.setId(saveJobArr[11].toString());
				
				listSaveJob.add(saveJob);
			}
		}
		
		return listSaveJob;
	}
	
	public List<SaveJob> getByJob(String jobId) {
		final String sql = "SELECT "
				+ "j.id, j.job_title, j.salary_start, j.salary_end, j.description, j.end_date, "
				+ "c.company_name, jp.position_name, js.status_name, et.employment_name "
				+ "FROM "
				+ "	t_save_job sj "
				+ "INNER JOIN "
				+ "	t_job tj  "
				+ "INNER JOIN  "
				+ "	t_company tc ON tc.id = tj.company_id  "
				+ "INNER JOIN  "
				+ "	t_city tci ON tci.id = tc.city_id  "
				+ "INNER JOIN  "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id "
				+ "WHERE "
				+ "j.id = :jobId ";
		
		final List<?> saveJobsObj = em().createNativeQuery(sql)
				.setParameter("jobId", jobId)
				.getResultList();
		
		final List<SaveJob> listSaveJob = new ArrayList<>();
		
		if(saveJobsObj.size() > 0) {
			for (Object saveJobObj : saveJobsObj) {
				final Object[] saveJobArr = (Object[]) saveJobObj;
				
				final Job job = new Job();
				job.setId(saveJobArr[0].toString());
				job.setJobTitle(saveJobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(saveJobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(saveJobArr[3].toString()));
				job.setDescription(saveJobArr[4].toString());
				job.setEndDate(LocalDate.parse(saveJobArr[5].toString()));
				
				final Company company = new Company();
				company.setCompanyName(saveJobArr[6].toString());
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(saveJobArr[7].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(saveJobArr[8].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(saveJobArr[9].toString());
				job.setEmployementType(employmentType);
				
				final SaveJob saveJob = new SaveJob();
				saveJob.setJob(job);
				
				listSaveJob.add(saveJob);
			}
		}
		
		return listSaveJob;
	}
	
	public List<SaveJob> getByJobAndCandidate(String jobId, String candidateId) {
		final String sql = "SELECT  "
				+ "tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date,  "
				+ "tc.company_name, tjp.position_name, tjs.status_name, tet.employment_name  "
				+ "FROM  "
				+ "	t_save_job tsj  "
				+ "INNER JOIN  "
				+ "	t_job tj ON tj.id = tsj.job_id "
				+ "INNER JOIN   "
				+ "	t_company tc ON tc.id = tj.company_id   "
				+ "INNER JOIN   "
				+ "	t_city tci ON tci.id = tc.city_id   "
				+ "INNER JOIN   "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id   "
				+ "INNER JOIN   "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id   "
				+ "INNER JOIN   "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "WHERE  "
				+ "tj.id = :jobId AND tsj.candidate_id = :candidateId ";
		
		final List<?> saveJobsObj = em().createNativeQuery(sql)
				.setParameter("jobId", jobId)
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		final List<SaveJob> listSaveJob = new ArrayList<>();
		
		if(saveJobsObj.size() > 0) {
			for (Object saveJobObj : saveJobsObj) {
				final Object[] saveJobArr = (Object[]) saveJobObj;
				
				final Job job = new Job();
				job.setId(saveJobArr[0].toString());
				job.setJobTitle(saveJobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(saveJobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(saveJobArr[3].toString()));
				job.setDescription(saveJobArr[4].toString());
				job.setEndDate(LocalDate.parse(saveJobArr[5].toString()));
				
				final Company company = new Company();
				company.setCompanyName(saveJobArr[6].toString());
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(saveJobArr[7].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(saveJobArr[8].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(saveJobArr[9].toString());
				job.setEmployementType(employmentType);
				
				final SaveJob saveJob = new SaveJob();
				saveJob.setJob(job);
				
				listSaveJob.add(saveJob);
			}
		}
		
		return listSaveJob;
	}
	
}
