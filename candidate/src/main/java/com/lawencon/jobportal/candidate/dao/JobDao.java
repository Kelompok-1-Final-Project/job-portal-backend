package com.lawencon.jobportal.candidate.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
import com.lawencon.jobportal.candidate.util.DateConvert;

@Repository
public class JobDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Job> getCountByIndustry(String industry) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ti.industry_name, tci.city_name, ");
		sql.append("tjp.position_name, tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = ti.id ");
		sql.append("WHERE ti.id ILIKE :industry || '%'");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("industry", industry)
				.getResultList();
		
		final List<Job> listJob = new ArrayList<>();
		
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final File file = new File();
				file.setId(jobArr[8].toString());
				company.setFile(file);
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[9].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[10].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[11].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[12].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[13].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(Timestamp.valueOf(jobArr[14].toString()).toLocalDateTime());
				if(jobArr[15] != null) {
					job.setUpdatedAt(Timestamp.valueOf(jobArr[15].toString()).toLocalDateTime());					
				}
				job.setVersion(Integer.valueOf(jobArr[16].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByIndustry(String industry, Integer startIndex, Integer endIndex) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ti.industry_name, tci.city_name, ");
		sql.append("tjp.position_name, tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = ti.id ");
		sql.append("WHERE ti.id ILIKE :industry || '%'");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("industry", industry)
				.setFirstResult(startIndex)
				.setMaxResults(endIndex)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final File file = new File();
				file.setId(jobArr[8].toString());
				company.setFile(file);
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[9].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[10].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[11].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[12].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[13].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(Timestamp.valueOf(jobArr[14].toString()).toLocalDateTime());
				if(jobArr[15] != null) {
					job.setUpdatedAt(Timestamp.valueOf(jobArr[15].toString()).toLocalDateTime());					
				}
				job.setVersion(Integer.valueOf(jobArr[16].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByName(String jobName) {
		final String sql = "SELECT  "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE  "
				+ "tj.job_title ILIKE '%' || :jobName || '%'";
		
		final List<?> jobsObj = this.em().createNativeQuery(sql)
				.setParameter("jobName", jobName)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByLocation(String location) {
		final String sql = "SELECT  "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE  "
				+ "city_name ILIKE :location || '%'";
		
		final List<?> jobsObj = this.em().createNativeQuery(sql)
				.setParameter("location", location)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public Job getByCode(String jobCode) {
		final String sql = "SELECT "
				+ "j "
				+ "FROM "
				+ "Job j "
				+ "WHERE "
				+ "j.jobCode = :jobCode";
		
		final Job job = em().createQuery(sql, Job.class)
				.setParameter("jobCode", jobCode)
				.getSingleResult();
		
		return job;
	}
	
	public List<Job> getByCompany(String companyName){
		final String sql = "SELECT   "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE   "
				+ "	company_name ILIKE :company || '%'";
		final List<?> jobsObj = this.em().createNativeQuery(sql, Job.class)
				.setParameter("company", companyName)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByEmploymentType(String employmentName){
		final String sql = "SELECT   "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE   "
				+ "	employment_name ILIKE :employment || '%'";
		final List<?> jobsObj = this.em().createNativeQuery(sql, Job.class)
				.setParameter("employment", employmentName)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByPosition(String positionName){
		final String sql = "SELECT   "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE   "
				+ "	position_name ILIKE :position || '%'";
		final List<?> jobsObj = this.em().createNativeQuery(sql, Job.class)
				.setParameter("position", positionName)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByStatus(String status){
		final String sql = "SELECT   "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE   "
				+ "	status_name ILIKE :status  || '%'";
		final List<?> jobsObj = this.em().createNativeQuery(sql, Job.class)
				.setParameter("status", status)
				.getResultList();
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getBySalary(Integer salaryStart, Integer salaryEnd){
		final String sql = "SELECT "
				+ "	tj.id, "
				+ "	tj.job_title, "
				+ "	tj.salary_start, "
				+ "	tj.salary_end, "
				+ "	tj.description, "
				+ "	tj.end_date, "
				+ " tc.id AS company_id "
				+ "	tc.company_name, "
				+ "	ti.industry_name, "
				+ "	tci.city_name, "
				+ "	tjp.position_name, "
				+ "	tjs.status_name, "
				+ "	tet.employment_name, "
				+ "	tj.created_at, "
				+ " tj.updated_at, "
				+ "	tj.ver "
				+ "FROM "
				+ "t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_city tci ON tci.id = tc.city_id "
				+ "INNER JOIN "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
				+ "INNER JOIN  "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
				+ "INNER JOIN  "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
				+ "INNER JOIN "
				+ "t_industry ti ON tc.industry_id = ti.id "
				+ "WHERE"
				+ "	tj.salary_start >= :start "
				+ "	AND "
				+ "	tj.salary_end <= :end ";
		
		final List<?> jobsObj = this.em().createNativeQuery(sql, Job.class)
				.setParameter("start", salaryStart)
				.setParameter("end", salaryEnd)
				.getResultList();
		
		final List<Job> listJob = new ArrayList<>();
		if(jobsObj.size() > 0) {
			for(Object jobObj:jobsObj) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[8].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[9].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[10].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[11].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[12].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(DateConvert.convertDate(jobArr[13].toString()));
				job.setUpdatedAt(DateConvert.convertDate(jobArr[14].toString()));
				job.setVersion(Integer.valueOf(jobArr[15].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> filterSearch(String name, String cityId, String positionId, List<String> employmentId, Integer salaryStart, Integer salaryEnd, Integer startIndex, Integer endIndex){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ti.industry_name, tci.city_name, ");
		sql.append("tjp.position_name, tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = ti.id ");
		sql.append("WHERE 1 = 1 ");
		
		if(name != null && !name.equalsIgnoreCase("")) {
			sql.append(" AND tj.job_title ILIKE :name || '%' ");
		}
		
		if(cityId != null && !cityId.equalsIgnoreCase("")) {
			sql.append(" AND tci.id ILIKE :city || '%' ");
		}
		
		if(positionId != null && !positionId.equalsIgnoreCase("")) {
			sql.append(" AND tjp.id ILIKE :position || '%' ");
		}
		
		if(!employmentId.isEmpty()) {
			sql.append("AND tet.id IN (:employment) ");
		}
		
		if(salaryStart != null) {
			sql.append(" AND tj.salary_start >= :start ");
		}
		
		if(salaryEnd != null) {
			sql.append(" AND tj.salary_end <= :end ");
		}
		
		final Query jobQuery = this.em().createNativeQuery(sql.toString());
		
		if(name != null && !name.equalsIgnoreCase("")) {
			jobQuery.setParameter("name", name);
		}
		
		if(cityId != null && !cityId.equalsIgnoreCase("")) {
			jobQuery.setParameter("city", cityId);
		}
		
		if(positionId != null && !positionId.equalsIgnoreCase("")) {
			jobQuery.setParameter("position", positionId);
		}
		
		if(!employmentId.isEmpty()) {
			jobQuery.setParameter("employment", employmentId);
		}
		
		if(salaryStart != null) {
			jobQuery.setParameter("start", salaryStart);
		}
		
		if(salaryEnd != null) {
			if(salaryEnd != 0) {
				jobQuery.setParameter("end", salaryEnd);				
			}
			else {
				jobQuery.setParameter("end", Integer.MAX_VALUE);
			}
		}
				
		final List<?> jobObjs = jobQuery
				.setFirstResult(startIndex)
				.setMaxResults(endIndex)
				.getResultList();
		
		final List<Job> listJob = new ArrayList<>();
		
		if(jobObjs.size() > 0) {
			for(Object jobObj : jobObjs) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final File file = new File();
				file.setId(jobArr[8].toString());
				company.setFile(file);
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[9].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[10].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[11].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[12].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[13].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(Timestamp.valueOf(jobArr[14].toString()).toLocalDateTime());
				if(jobArr[16] != null) {
					job.setUpdatedAt(Timestamp.valueOf(jobArr[15].toString()).toLocalDateTime());					
				}
				job.setVersion(Integer.valueOf(jobArr[16].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> filterCountSearch(String name, String cityId, String positionId, List<String> employmentId, Integer salaryStart, Integer salaryEnd){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, tc.file_id, ti.industry_name, tci.city_name, ");
		sql.append("tjp.position_name, tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = ti.id ");
		sql.append("WHERE 1 = 1 ");
		
		if(name != null && !name.equalsIgnoreCase("")) {
			sql.append(" AND tj.job_title ILIKE :name || '%' ");
		}
		
		if(cityId != null && !cityId.equalsIgnoreCase("")) {
			sql.append(" AND tci.id ILIKE :city || '%' ");
		}
		
		if(positionId != null && !positionId.equalsIgnoreCase("")) {
			sql.append(" AND tjp.id ILIKE :position || '%' ");
		}
		
		if(!employmentId.isEmpty()) {
			sql.append("AND tet.id IN (:employment) ");
		}
		
		if(salaryStart != null) {
			sql.append(" AND tj.salary_start >= :start ");
		}
		
		if(salaryEnd != null) {
			sql.append(" AND tj.salary_end <= :end ");
		}
		
		final Query jobQuery = this.em().createNativeQuery(sql.toString());
		
		if(name != null && !name.equalsIgnoreCase("")) {
			jobQuery.setParameter("name", name);
		}
		
		if(cityId != null && !cityId.equalsIgnoreCase("")) {
			jobQuery.setParameter("city", cityId);
		}
		
		if(positionId != null && !positionId.equalsIgnoreCase("")) {
			jobQuery.setParameter("position", positionId);
		}
		
		if(!employmentId.isEmpty()) {
			jobQuery.setParameter("employment", employmentId);
		}
		
		if(salaryStart != null) {
			jobQuery.setParameter("start", salaryStart);
		}
		
		if(salaryEnd != null) {
			if(salaryEnd != 0) {
				jobQuery.setParameter("end", salaryEnd);				
			}
			else {
				jobQuery.setParameter("end", Integer.MAX_VALUE);
			}
		}
				
		final List<?> jobObjs = jobQuery
				.getResultList();
		
		final List<Job> listJob = new ArrayList<>();
		
		if(jobObjs.size() > 0) {
			for(Object jobObj : jobObjs) {
				final Object[] jobArr = (Object[]) jobObj;
				
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobTitle(jobArr[1].toString());
				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
				job.setDescription(jobArr[4].toString());
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				
				final Company company = new Company();
				company.setId(jobArr[6].toString());
				company.setCompanyName(jobArr[7].toString());
				
				final File file = new File();
				file.setId(jobArr[8].toString());
				company.setFile(file);
				
				final Industry industrySet = new Industry();
				industrySet.setIndustryName(jobArr[9].toString());
				company.setIndustry(industrySet);
				
				final City city = new City();
				city.setCityName(jobArr[10].toString());
				company.setCity(city);
				
				job.setCompany(company);
				
				final JobPosition jobPosition = new JobPosition();
				jobPosition.setPositionName(jobArr[11].toString());
				job.setJobPosition(jobPosition);
				
				final JobStatus jobStatus = new JobStatus();
				jobStatus.setStatusName(jobArr[12].toString());
				job.setJobStatus(jobStatus);
				
				final EmploymentType employmentType = new EmploymentType();
				employmentType.setEmploymentName(jobArr[13].toString());
				job.setEmployementType(employmentType);
				
				job.setCreatedAt(Timestamp.valueOf(jobArr[14].toString()).toLocalDateTime());
				if(jobArr[16] != null) {
					job.setUpdatedAt(Timestamp.valueOf(jobArr[15].toString()).toLocalDateTime());					
				}
				job.setVersion(Integer.valueOf(jobArr[16].toString()));
				listJob.add(job);
			}
		}
		
		return listJob;
	}
}
