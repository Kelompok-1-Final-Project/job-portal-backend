package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.PersonType;

@Repository
public class PersonTypeDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public PersonType getByCode(String code) {
		final String sql = "SELECT " 
				+ "id, type_name, type_code, ver " 
				+ "FROM " 
				+ "t_person_type " 
				+ "WHERE "
				+ "type_code = :code";

		final Object personTypeObj = em().createNativeQuery(sql)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] personTypeArr = (Object[]) personTypeObj;

		PersonType personType = null;

		if (personTypeArr.length > 0) {
			personType = new PersonType();
			personType.setId(personTypeArr[0].toString());
			personType.setTypeName(personTypeArr[1].toString());
			personType.setTypeCode(personTypeArr[2].toString());
			personType.setVersion(Integer.valueOf(personTypeArr[3].toString()));
		}

		return personType;

	}
}
