package com.lawencon.jobportal.admin.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.City;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.EmploymentType;
import com.lawencon.jobportal.admin.model.Industry;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobPosition;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.model.Profile;
import com.lawencon.jobportal.admin.model.User;
import com.lawencon.jobportal.admin.util.DateConvert;

@Repository
public class JobDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Job> getByUser(String userId){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = ti.id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE tj.hr_id = :userId OR tj.interviewer_id = :userId ");

		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
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
				
				final City citySet = new City();
				citySet.setCityName(jobArr[9].toString());
				company.setCity(citySet);
				
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
				
				job.setCreatedAt(Timestamp.valueOf(jobArr[13].toString()).toLocalDateTime());
				if(jobArr[14]!=null) {
					job.setUpdatedAt(Timestamp.valueOf(jobArr[14].toString()).toLocalDateTime());					
				}
				job.setVersion(Integer.valueOf(jobArr[15].toString()));

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setInterviewer(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByIndustry(String industry, Integer startPosition, Integer endPosition) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, ");
		sql.append("tj.ver, tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE ti.industry_name  ILIKE '%' || :industry || '%' ");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("industry", industry)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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
				
				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByName(String jobName, Integer startPosition, Integer endPosition) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE tj.job_title ILIKE '%' || :jobName || '%'");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("jobName", jobName)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByLocation(String location, Integer startPosition, Integer endPosition) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE city_name ILIKE :location || '%'");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("location", location)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public Job getByCode(String jobCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT j FROM Job j WHERE j.jobCode = :jobCode");
		
		final Job job = em().createQuery(sql.toString(), Job.class)
				.setParameter("jobCode", jobCode)
				.getSingleResult();
		
		return job;
	}
	
	public List<Job> getByCompany(String companyName){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE company_name ILIKE :company || '%'");

		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
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
				
				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByEmploymentType(String employmentName, Integer startPosition, Integer endPosition){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE employment_name ILIKE :employment || '%'");

		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("employment", employmentName)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByPosition(String positionName, Integer startPosition, Integer endPosition){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE position_name ILIKE :position || '%'");

		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("position", positionName)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getByStatus(String status, Integer startPosition, Integer endPosition){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE status_name ILIKE :status  || '%'");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("status", status)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> getBySalary(Integer salaryStart, Integer salaryEnd, Integer startPosition, Integer endPosition){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tj.id, tj.job_title, tj.salary_start, tj.salary_end, tj.description, tj.end_date, ");
		sql.append("tc.id AS company_id, tc.company_name, ti.industry_name, tci.city_name, tjp.position_name, ");
		sql.append("tjs.status_name, tet.employment_name, tj.created_at, tj.updated_at, tj.ver, ");
		sql.append("tpi.full_name AS interviewer, tph.full_name AS hr, tj.job_code ");
		sql.append("FROM t_job tj ");
		sql.append("INNER JOIN t_company tc ON tc.id = tj.company_id ");
		sql.append("INNER JOIN t_city tci ON tci.id = tc.city_id ");
		sql.append("INNER JOIN t_job_position tjp ON tjp.id = tj.job_position_id ");
		sql.append("INNER JOIN t_job_status tjs ON tjs.id = tj.job_status_id ");
		sql.append("INNER JOIN t_employment_type tet ON tet.id = tj.employment_type_id ");
		sql.append("INNER JOIN t_industry ti ON tc.industry_id = tc.industry_id ");
		sql.append("INNER JOIN t_user tuh ON tj.hr_id = tuh.id ");
		sql.append("INNER JOIN t_user tui ON tj.interviewer_id = tui.id ");
		sql.append("INNER JOIN t_profile tph ON tuh.profile_id = tph.id ");
		sql.append("INNER JOIN t_profile tpi ON tui.profile_id = tpi.id ");
		sql.append("WHERE tj.salary_start >= :start AND tj.salary_end <= :end ");
		
		final List<?> jobsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("start", salaryStart)
				.setParameter("end", salaryEnd)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
	
	public List<Job> filterSearch(String city, String position, String employment, Integer salaryStart, Integer salaryEnd, Integer startPosition, Integer endPosition){
		final String sql = "SELECT  "
				+ "	tj.id,  "
				+ "	tj.job_title,  "
				+ "	tj.salary_start,  "
				+ "	tj.salary_end,  "
				+ "	tj.description,  "
				+ "	tj.end_date,  "
				+ " tc.id AS company_id , "
				+ "	tc.company_name,  "
				+ "	ti.industry_name,  "
				+ "	tci.city_name,  "
				+ "	tjp.position_name,  "
				+ "	tjs.status_name,  "
				+ "	tet.employment_name,  "
				+ "	tj.created_at,  "
				+ " tj.updated_at,  "
				+ "	tj.ver, "
				+ "	tpi.full_name AS interviewer, "
				+ "	tph.full_name AS hr "
				+ " tj.job_code "
				+ "FROM  "
				+ "	t_job tj  "
				+ "INNER JOIN  "
				+ "	t_company tc ON tc.id = tj.company_id  "
				+ "INNER JOIN  "
				+ "	t_city tci ON tci.id = tc.city_id  "
				+ "INNER JOIN  "
				+ "	t_job_position tjp ON tjp.id = tj.job_position_id   "
				+ "INNER JOIN   "
				+ "	t_job_status tjs ON tjs.id = tj.job_status_id   "
				+ "INNER JOIN   "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id   "
				+ "INNER JOIN  "
				+ "	t_industry ti ON tc.industry_id = tc.industry_id  "
				+ "INNER JOIN  "
				+ "	t_user tuh ON tj.hr_id = tuh.id  "
				+ "INNER JOIN "
				+ "	t_user tui ON tj.interviewer_id = tui.id  "
				+ "INNER JOIN  "
				+ "	t_profile tph ON tuh.profile_id = tph.id  "
				+ "INNER JOIN "
				+ "	t_profile tpi ON tui.profile_id = tpi.id "
				+ "WHERE"
				+ "	tci.city_name ILIKE :city || '%' "
				+ "	AND "
				+ "	tjp.position_name ILIKE :position || '%' "
				+ "	AND "
				+ "	tet.employment_name ILIKE :employment || '%' "
				+ "	AND"
				+ "	tj.salary_start >= :start "
				+ "	AND "
				+ "	tj.salary_end <= :end ";
		
		String cityParam = "";
		String positionParam = "";
		String employmentParam = "";
		Integer salaryStartParam = 0;
		Integer salaryEndParam = 0;
		
		if(city != null) {
			cityParam = city;
		}
		
		if(position != null) {
			positionParam = position;
		}
		
		if(employment != null) {
			employmentParam = employment;
		}
		
		if(salaryStart != null) {
			salaryStartParam = salaryStart;
		}
		
		if(salaryEnd == null) {
			salaryEndParam = Integer.MAX_VALUE;
		}
		else if(salaryEnd != null) {
			if (salaryEnd == 0) {
				salaryEndParam = Integer.MAX_VALUE;
			}
			else {
				salaryEndParam = salaryEnd;
			}
		}
		
		
		final List<?> jobsObj = this.em().createNativeQuery(sql, Job.class)
				.setParameter("city", cityParam)
				.setParameter("position", positionParam)
				.setParameter("employment", employmentParam)
				.setParameter("start", salaryStartParam)
				.setParameter("end", salaryEndParam)
				.setFirstResult(startPosition)
				.setMaxResults(endPosition)
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
				
				final City citySet = new City();
				citySet.setCityName(jobArr[9].toString());
				company.setCity(citySet);
				
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

				final Profile profileHr = new Profile();
				profileHr.setFullName(jobArr[16].toString());
				final User userHr = new User();
				userHr.setProfile(profileHr);
				job.setHr(userHr);
				
				final Profile profileInterviewer = new Profile();
				profileInterviewer.setFullName(jobArr[17].toString());
				final User userInterviewer = new User();
				userInterviewer.setProfile(profileInterviewer);
				job.setHr(userInterviewer);
				
				job.setJobCode(jobArr[18].toString());
				listJob.add(job);
			}
		}
		
		return listJob;
	}
}
