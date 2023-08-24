package com.lawencon.jobportal.admin.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Profile;
import com.lawencon.jobportal.admin.model.Role;
import com.lawencon.jobportal.admin.model.User;

@Repository
public class UserDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public User getByEmail(String userEmail) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.id AS user_id, p.full_name, r.role_code, u.is_active, u.pass ");
		sql.append("FROM t_user u ");
		sql.append("INNER JOIN t_role r ON r.id = u.role_id ");
		sql.append("INNER JOIN t_profile p ON p.id = u.profile_id ");
		sql.append("WHERE u.email = :userEmail ");

		try {
			final Object userObj = em().createNativeQuery(sql.toString())
					.setParameter("userEmail", userEmail)
					.getSingleResult();
			
			final Object[] userArr = (Object[]) userObj;
			
			User user = null;

			if(userArr.length > 0) {
				user = new User();
				user.setId(userArr[0].toString());
							
				final Profile profile = new Profile();
				profile.setFullName(userArr[1].toString());
				user.setProfile(profile);
				
				final Role role = new Role();
				role.setRoleCode(userArr[2].toString());
				user.setRole(role);
				
				user.setIsActive(Boolean.valueOf(userArr[3].toString()));
				user.setPass(userArr[4].toString());
			}
			return user;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getByRoleCode(String roleCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT u FROM User u WHERE u.role.roleCode = :roleCode ");
		
		final List<User> user = em().createQuery(sql.toString(), User.class)
				.setParameter("roleCode", roleCode)
				.getResultList();
		
		return user;	
	}
}
