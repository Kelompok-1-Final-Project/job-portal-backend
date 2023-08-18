package com.lawencon.jobportal.admin.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.jobportal.admin.model.City;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.EmploymentType;
import com.lawencon.jobportal.admin.model.Industry;
import com.lawencon.jobportal.admin.model.Job;
import com.lawencon.jobportal.admin.model.JobPosition;
import com.lawencon.jobportal.admin.model.JobStatus;
import com.lawencon.jobportal.admin.util.DateConvert;

@Repository
public class ApplicationDao extends AbstractJpaDao {
	
	
//	public List<Job> getByCandidate(String candidateId) {
//		final String sql = "SELECT "
//				+ "	tj.id, "
//				+ "	tj.job_title, "
//				+ "	tj.salary_start, "
//				+ "	tj.salary_end, "
//				+ "	tj.description, "
//				+ "	tj.end_date, "
//				+ "	tc.company_name, "
//				+ "	ti.industry_name, "
//				+ "	tci.city_name, "
//				+ "	tjp.position_name, "
//				+ "	tjs.status_name, "
//				+ "	tet.employment_name, "
//				+ "	tj.created_at, "
//				+ " tj.updated_at, "
//				+ "	tj.ver "
//				+ "FROM "
//				+ "t_job tj "
//				+ "INNER JOIN "
//				+ "	t_company tc ON tc.id = tj.company_id "
//				+ "INNER JOIN "
//				+ "	t_city tci ON tci.id = tc.city_id "
//				+ "INNER JOIN "
//				+ "	t_job_position tjp ON tjp.id = tj.job_position_id  "
//				+ "INNER JOIN  "
//				+ "	t_job_status tjs ON tjs.id = tj.job_status_id  "
//				+ "INNER JOIN  "
//				+ "	t_employment_type tet ON tet.id = tj.employment_type_id  "
//				+ "INNER JOIN "
//				+ "t_industry ti ON tc.industry_id = tc.industry_id "
//				+ "WHERE  "
//				+ "ti.industry_name  ILIKE '%' || :industry || '%'";
//		
//		final List<?> jobsObj = this.em().createNativeQuery(sql)
//				.setParameter("industry", industry)
//				.getResultList();
//		final List<Job> listJob = new ArrayList<>();
//		if(jobsObj.size() > 0) {
//			for(Object jobObj:jobsObj) {
//				final Object[] jobArr = (Object[]) jobObj;
//				
//				final Job job = new Job();
//				job.setId(jobArr[0].toString());
//				job.setJobTitle(jobArr[1].toString());
//				job.setSalaryStart(Integer.valueOf(jobArr[2].toString()));
//				job.setSalaryEnd(Integer.valueOf(jobArr[3].toString()));
//				job.setDescription(jobArr[4].toString());
//				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
//				
//				final Company company = new Company();
//				company.setCompanyName(jobArr[6].toString());
//				
//				final Industry industrySet = new Industry();
//				industrySet.setIndustryName(jobArr[7].toString());
//				company.setIndustry(industrySet);
//				
//				final City city = new City();
//				city.setCityName(jobArr[8].toString());
//				company.setCity(city);
//				
//				job.setCompany(company);
//				
//				final JobPosition jobPosition = new JobPosition();
//				jobPosition.setPositionName(jobArr[9].toString());
//				job.setJobPosition(jobPosition);
//				
//				final JobStatus jobStatus = new JobStatus();
//				jobStatus.setStatusName(jobArr[10].toString());
//				job.setJobStatus(jobStatus);
//				
//				final EmploymentType employmentType = new EmploymentType();
//				employmentType.setEmploymentName(jobArr[11].toString());
//				job.setEmployementType(employmentType);
//				
//				job.setCreatedAt(DateConvert.convertDate(jobArr[12].toString()));
//				job.setUpdatedAt(DateConvert.convertDate(jobArr[13].toString()));
//				job.setVersion(Integer.valueOf(jobArr[14].toString()));
//				listJob.add(job);
//			}
//		}
//		
//		return listJob;
//	}
}
