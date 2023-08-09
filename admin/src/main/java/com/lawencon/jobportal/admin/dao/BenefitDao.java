package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Benefit;

@Repository
public class BenefitDao extends AbstractJpaDao {
	private final EntityManager em = ConnHandler.getManager();

	public Benefit getByCode(String code) {
		final String sql = "SELECT "
				+ "id, benefit_name, benefit_code, ver " 
				+ "FROM " 
				+ "t_benefit " 
				+ "WHERE "
				+ "benefit_code = :code";

		final Object benefitObj = this.em.createNativeQuery(sql, Benefit.class)
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
}
