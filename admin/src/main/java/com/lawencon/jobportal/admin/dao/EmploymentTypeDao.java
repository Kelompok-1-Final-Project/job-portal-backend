package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.EmploymentType;

@Repository
public class EmploymentTypeDao extends AbstractJpaDao {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public EmploymentType getByCode(String code) {
		final String sql = "SELECT "
				+ "id, employment_code, employment_name, ver " 
				+ "FROM " 
				+ "t_employment_type " 
				+ "WHERE "
				+ "employment_code = :code";

		final Object employmentTypeObj = this.em().createNativeQuery(sql, EmploymentType.class)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] employmentTypeArr = (Object[]) employmentTypeObj;

		EmploymentType employmentType = null;

		if (employmentTypeArr.length > 0) {
			employmentType = new EmploymentType();
			employmentType.setId(employmentTypeArr[0].toString());
			employmentType.setEmploymentCode(employmentTypeArr[1].toString());
			employmentType.setEmploymentName(employmentTypeArr[2].toString());
			employmentType.setVersion(Integer.valueOf(employmentTypeArr[3].toString()));
		}

		return employmentType;
	}
}
