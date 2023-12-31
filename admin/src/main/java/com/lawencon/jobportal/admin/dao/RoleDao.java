package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Role;

@Repository
public class RoleDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Role getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, role_name, role_code, ver ");
		sql.append("FROM t_role ");
		sql.append("WHERE role_code = :code ");
		
		final Object roleObj = em().createNativeQuery(sql.toString())
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
