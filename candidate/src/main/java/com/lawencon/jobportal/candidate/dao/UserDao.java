package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Profile;
import com.lawencon.jobportal.candidate.model.User;

@Repository
public class UserDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public User getByEmail(String userEmail) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.id AS user_id, p.full_name, u.is_active, u.pass, p.photo_id, u.email ");
		sql.append("FROM t_user u ");
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
				
				final File file = new File();
				if(userArr[4] != null) {
					file.setId(userArr[4].toString());
					profile.setPhoto(file);					
				}
				
				user.setProfile(profile);
				
				user.setIsActive(Boolean.valueOf(userArr[2].toString()));
				user.setPass(userArr[3].toString());
				user.setEmail(userArr[5].toString());
			}
			return user;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
