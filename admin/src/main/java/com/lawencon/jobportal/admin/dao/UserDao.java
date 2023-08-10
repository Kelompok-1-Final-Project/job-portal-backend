package com.lawencon.jobportal.admin.dao;

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
		final String sql = "SELECT "
				+ "u.id AS user_id, p.full_name, r.role_code, u.is_active, u.pass "
				+ "FROM "
				+ "t_user u "
				+ "INNER JOIN "
				+ "t_user_role r ON r.id = u.role_id "
				+ "INNER JOIN "
				+ "t_profile p ON p.id = u.profile_id "
				+ "WHERE "
				+ "u.email = :userEmail ";
		try {
			final Object userObj = em().createNativeQuery(sql)
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
}
