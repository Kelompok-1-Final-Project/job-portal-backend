package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Benefit;
import com.lawencon.jobportal.admin.model.JobBenefit;

@Repository
public class JobBenefitDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public JobBenefit getByJobAndBenefit(String jobId, String benefitId){
		final String sql = "SELECT  "
				+ "	tjb.id, tb.benefit_name, tjb.ver  "
				+ "FROM  "
				+ "	t_job_benefit tjb  "
				+ "INNER JOIN  "
				+ "	t_job tj ON tj.id = tjb.job_id  "
				+ "INNER JOIN  "
				+ "	t_benefit tb ON tb.id = tjb.benefit_id  "
				+ "WHERE  "
				+ "	tjb.job_id = :jobId AND tjb.benefit_id = :benefitId";
		
		try {
			final Object jobBenefitObjs = em().createNativeQuery(sql)
					.setParameter("jobId", jobId)
					.setParameter("benefitId", benefitId)
					.getSingleResult();
			
			final Object[] jobBenefitArr = (Object[]) jobBenefitObjs;
			
			JobBenefit jobBenefit = null;

			if(jobBenefitArr.length > 0) {
				jobBenefit = new JobBenefit();
				jobBenefit.setId(jobBenefitArr[0].toString());
				final Benefit benefit = new Benefit();
				benefit.setBenefitName(jobBenefitArr[1].toString());
				jobBenefit.setBenefit(benefit);
				jobBenefit.setVersion(Integer.valueOf(jobBenefitArr[2].toString()));
			}
			return jobBenefit;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
