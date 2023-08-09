package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.jobportal.admin.model.Role;

@Repository
public class RoleDao extends AbstractJpaDao{

	@PersistenceContext
	private EntityManager em;
	
	public Role getByCode(String code) {
		final String sql = "SELECT "
				+ "id, role_name, role_code, ver "
				+ "FROM "
				+ "t_role "
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
