package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Gender;

@Repository
public class GenderDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Gender getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, gender_name, gender_code, ver ");
		sql.append("FROM t_gender ");
		sql.append("WHERE gender_code = :code ");
		
		final Object genderObj = this.em().createNativeQuery(sql.toString())
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] genderArr = (Object[]) genderObj;
		
		Gender gender = null;
		
		if (genderArr.length > 0) {
			gender = new Gender();
			gender.setId(genderArr[0].toString());
			gender.setGenderName(genderArr[1].toString());
			gender.setGenderCode(genderArr[2].toString());
			gender.setVersion(Integer.valueOf(genderArr[3].toString()));
		}
		
		return gender;
	}
}
