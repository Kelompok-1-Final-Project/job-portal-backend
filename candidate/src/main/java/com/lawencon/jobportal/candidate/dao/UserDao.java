package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Profile;
import com.lawencon.jobportal.candidate.model.User;

@Repository
public class UserDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public User getByEmail(String userEmail) {
		final String sql = "SELECT "
				+ "u.id AS user_id, p.full_name, u.is_active, u.pass "
				+ "FROM "
				+ "t_user u "
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
				
				user.setIsActive(Boolean.valueOf(userArr[2].toString()));
				user.setPass(userArr[3].toString());
			}
			return user;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
