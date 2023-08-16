package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.MaritalStatus;

@Repository
public class MaritalStatusDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public MaritalStatus getByCode(String code) {
		final String sql = "SELECT "
				+ "id, status_name, status_code, ver " 
				+ "FROM " 
				+ "t_marital_status " 
				+ "WHERE "
				+ "status_code = :code";

		final Object maritalObj = em().createNativeQuery(sql)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] maritalArr = (Object[]) maritalObj;

		MaritalStatus maritalStatus = null;

		if (maritalArr.length > 0) {
			maritalStatus = new MaritalStatus();
			maritalStatus.setId(maritalArr[0].toString());
			maritalStatus.setStatusName(maritalArr[1].toString());
			maritalStatus.setStatusCode(maritalArr[2].toString());
			maritalStatus.setVersion(Integer.valueOf(maritalArr[3].toString()));
		}

		return maritalStatus;
	}
}
