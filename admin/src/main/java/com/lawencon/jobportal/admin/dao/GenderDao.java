package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Gender;

@Repository
public class GenderDao extends AbstractJpaDao{
	private final EntityManager em = ConnHandler.getManager();
	
	public Gender getByCode(String code) {
		final String sql = "SELECT "
				+ "id, gender_name, gender_code, ver "
				+ "FROM "
				+ "t_gender "
				+ "WHERE "
				+ "role_code = :code";
		
		final Object roleObj = this.em.createNativeQuery(sql, Role.class)
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] roleArr = (Object[]) roleObj;
		
		Role role = null;
		
		if (roleArr.length > 0) {
			role = new Role();
			role.setId(roleArr[0].toString());
			role.setRoleName(roleArr[1].toString());
			role.setRoleCode(roleArr[2].toString());
			role.setVersion(Integer.valueOf(roleArr[3].toString()));
		}
		
		return role;
	}
}
