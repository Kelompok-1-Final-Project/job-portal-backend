package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Benefit;

@Repository
public class BenefitDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Benefit getByCode(String code) {
		final String sql = "SELECT "
				+ "id, benefit_name, benefit_code, ver " 
				+ "FROM " 
				+ "t_benefit " 
				+ "WHERE "
				+ "benefit_code = :code";

		final Object benefitObj = em().createNativeQuery(sql, Benefit.class)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] benefitArr = (Object[]) benefitObj;

		Benefit benefit = null;

		if (benefitArr.length > 0) {
			benefit = new Benefit();
			benefit.setId(benefitArr[0].toString());
			benefit.setBenefitName(benefitArr[1].toString());
			benefit.setBenefitCode(benefitArr[2].toString());
			benefit.setVersion(Integer.valueOf(benefitArr[3].toString()));
		}

		return benefit;
	}
	
	public Benefit getByName(String benefitName) {
		final String sql = "SELECT "
				+ "id, benefit_name, benefit_code, ver " 
				+ "FROM " 
				+ "t_benefit " 
				+ "WHERE "
				+ "benefit_name = :benefitName";

		final Object benefitObj = em().createNativeQuery(sql, Benefit.class)
				.setParameter("benefitName", benefitName)
				.getSingleResult();

		final Object[] benefitArr = (Object[]) benefitObj;

		Benefit benefit = null;

		if (benefitArr.length > 0) {
			benefit = new Benefit();
			benefit.setId(benefitArr[0].toString());
			benefit.setBenefitName(benefitArr[1].toString());
			benefit.setBenefitCode(benefitArr[2].toString());
			benefit.setVersion(Integer.valueOf(benefitArr[3].toString()));
		}

		return benefit;
	}
}
