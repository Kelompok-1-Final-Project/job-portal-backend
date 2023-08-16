package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Company;

@Repository
public class CompanyDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public Company getByCode(String companyCode) {
		final String sql = "SELECT "
				+ "c "
				+ "FROM "
				+ "Company c "
				+ "WHERE "
				+ "c.companyCode = :companyCode";
		
		final Company company = em().createQuery(sql, Company.class)
				.setParameter("companyCode", companyCode)
				.getSingleResult();
		
		return company;
	}
}
