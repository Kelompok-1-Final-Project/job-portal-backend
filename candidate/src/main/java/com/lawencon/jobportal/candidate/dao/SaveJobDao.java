package com.lawencon.jobportal.candidate.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.City;
import com.lawencon.jobportal.candidate.model.Company;
import com.lawencon.jobportal.candidate.model.EmploymentType;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Industry;
import com.lawencon.jobportal.candidate.model.Job;
import com.lawencon.jobportal.candidate.model.JobPosition;
import com.lawencon.jobportal.candidate.model.JobStatus;
import com.lawencon.jobportal.candidate.model.SaveJob;

@Repository
public class SaveJobDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<SaveJob> getByCandidateNonPagination(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.company_name, tjp.position_name, tjs.status_name, tet.employment_name, sj.ver, ");
		sql.append("sj.id AS saved_job_id, sj.created_at, sj.updated_at, ti.industry_name, tci.city_name, ");
		sql.append("tc.id AS company_id, tc.file_id AS company_photo ");
		sql.append("FROM t_save_job sj ");
		sql.append("INNER JOIN t_job tj ON tj.id = sj.job_id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON ti.id = tc.industry_id ");
		sql.append("WHERE sj.candidate_id = :candidateId ");
		
		final List<?> saveJobsObj = em().createNativeQuery(sql.toString())
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
				company.setId(saveJobArr[16].toString());
				
				final File file = new File();
				file.setId(saveJobArr[17].toString());
				company.setFile(file);
				
				final Industry industry = new Industry();
				industry.setIndustryName(saveJobArr[14].toString());
				company.setIndustry(industry);
				
				final City city = new City();
				city.setCityName(saveJobArr[15].toString());
				company.setCity(city);
				
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
				saveJob.setCreatedAt(Timestamp.valueOf(saveJobArr[12].toString()).toLocalDateTime());
				
				if(saveJobArr[13] != null) {
					saveJob.setUpdatedAt(Timestamp.valueOf(saveJobArr[13].toString()).toLocalDateTime());					
				}
				
				
				listSaveJob.add(saveJob);
			}
		}
		
		return listSaveJob;
	}
	
	public List<SaveJob> getByCandidate(String candidateId, Integer startIndex, Integer endIndex) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.company_name, tjp.position_name, tjs.status_name, tet.employment_name, sj.ver, ");
		sql.append("sj.id AS saved_job_id, sj.created_at, sj.updated_at, ti.industry_name, tci.city_name, ");
		sql.append("tc.id AS company_id, tc.file_id AS company_photo ");
		sql.append("FROM t_save_job sj ");
		sql.append("INNER JOIN t_job tj ON tj.id = sj.job_id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON ti.id = tc.industry_id ");
		sql.append("WHERE sj.candidate_id = :candidateId ");
		
		final List<?> saveJobsObj = em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.setFirstResult(startIndex)
				.setMaxResults(endIndex)
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
				company.setId(saveJobArr[16].toString());
				
				final File file = new File();
				file.setId(saveJobArr[17].toString());
				company.setFile(file);
				
				final Industry industry = new Industry();
				industry.setIndustryName(saveJobArr[14].toString());
				company.setIndustry(industry);
				
				final City city = new City();
				city.setCityName(saveJobArr[15].toString());
				company.setCity(city);
				
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
				saveJob.setCreatedAt(Timestamp.valueOf(saveJobArr[12].toString()).toLocalDateTime());
				
				if(saveJobArr[13] != null) {
					saveJob.setUpdatedAt(Timestamp.valueOf(saveJobArr[13].toString()).toLocalDateTime());					
				}
				
				
				listSaveJob.add(saveJob);
			}
		}
		
		return listSaveJob;
	}
	
	public List<SaveJob> getByJob(String jobId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT j.id, j.job_title, j.salary_start, j.salary_end, j.description, j.end_date, ");
		sql.append("c.company_name, jp.position_name, js.status_name, et.employment_name ");
		sql.append("FROM t_save_job sj ");
		sql.append("INNER JOIN t_job tj ON sj.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("WHERE tj.id = :jobId ");

		final List<?> saveJobsObj = em().createNativeQuery(sql.toString())
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
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT j.id, j.job_title, j.salary_start, j.salary_end, j.description, j.end_date, ");
		sql.append("c.company_name, jp.position_name, js.status_name, et.employment_name ");
		sql.append("FROM t_save_job sj ");
		sql.append("INNER JOIN t_job tj ON sj.job_id = tj.id ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("WHERE tj.id = :jobId AND tsj.candidate_id = :candidateId ");
		
		final List<?> saveJobsObj = em().createNativeQuery(sql.toString())
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
