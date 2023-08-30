package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Industry;

@Repository
public class IndustryDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Industry getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, industry_name, industry_code, ver ");
		sql.append("FROM t_industry ");
		sql.append("WHERE industry_code = :code ");

		final Object industryObj = em().createNativeQuery(sql.toString())
				.setParameter("code", code)
				.getSingleResult();

		final Object[] industryArr = (Object[]) industryObj;

		Industry industry = null;

		if (industryArr.length > 0) {
			industry = new Industry();
			industry.setId(industryArr[0].toString());
			industry.setIndustryName(industryArr[1].toString());
			industry.setIndustryCode(industryArr[2].toString());
			industry.setVersion(Integer.valueOf(industryArr[3].toString()));
		}

		return industry;
	}
	
	public Industry getByName(String industryName) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, industry_name, industry_code, ver ");
		sql.append("FROM t_industry ");
		sql.append("WHERE industry_name = :industryName ");

		final Object industryObj = em().createNativeQuery(sql.toString())
				.setParameter("industryName", industryName)
				.getSingleResult();

		final Object[] industryArr = (Object[]) industryObj;

		Industry industry = null;

		if (industryArr.length > 0) {
			industry = new Industry();
			industry.setId(industryArr[0].toString());
			industry.setIndustryName(industryArr[1].toString());
			industry.setIndustryCode(industryArr[2].toString());
			industry.setVersion(Integer.valueOf(industryArr[3].toString()));
		}

		return industry;
	}
}
