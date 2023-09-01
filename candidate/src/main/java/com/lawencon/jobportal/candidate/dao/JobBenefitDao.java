package com.lawencon.jobportal.candidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Benefit;
import com.lawencon.jobportal.candidate.model.JobBenefit;

@Repository
public class JobBenefitDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<JobBenefit> getByJob(String jobId){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tjb.id, tb.benefit_name, tjb.ver ");
		sql.append("FROM t_job_benefit tjb ");
		sql.append("INNER JOIN t_job tj ON tj.id = tjb.job_id ");
		sql.append("INNER JOIN t_benefit tb ON tb.id = tjb.benefit_id ");
		sql.append("WHERE tjb.job_id = :jobId ");
		
		final List<?> benefitsObj = this.em().createNativeQuery(sql.toString())
				.setParameter("jobId", jobId)
				.getResultList();
		
		final List<JobBenefit> listJobBenefit = new ArrayList<>();
		if(benefitsObj.size() > 0) {
			for(Object obj:benefitsObj) {
				final Object[] benefitArr = (Object[]) obj;
				final JobBenefit jobBenefit = new JobBenefit();
				jobBenefit.setId(benefitArr[0].toString());
				final Benefit benefit = new Benefit();
				benefit.setBenefitName(benefitArr[1].toString());
				jobBenefit.setBenefit(benefit);
				jobBenefit.setVersion(Integer.valueOf(benefitArr[2].toString()));
				listJobBenefit.add(jobBenefit);
			}
		}
		return listJobBenefit;
	}
	
	public JobBenefit getByJobAndBenefit(String jobId, String benefitId){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tjb.id, tb.benefit_name, tjb.ver ");
		sql.append("FROM t_job_benefit tjb ");
		sql.append("INNER JOIN t_job tj ON tj.id = tjb.job_id ");
		sql.append("INNER JOIN t_benefit tb ON tb.id = tjb.benefit_id ");
		sql.append("WHERE tjb.job_id = :jobId AND tjb.benefit_id = :benefitId ");

		try {
			final Object jobBenefitObjs = em().createNativeQuery(sql.toString())
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
