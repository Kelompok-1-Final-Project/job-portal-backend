package com.lawencon.jobportal.candidate.dao;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.File;
import com.lawencon.jobportal.candidate.model.Gender;
import com.lawencon.jobportal.candidate.model.MaritalStatus;
import com.lawencon.jobportal.candidate.model.PersonType;
import com.lawencon.jobportal.candidate.model.Profile;

@Repository
public class ProfileDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	
	public Profile getByUser(String userId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tp.id, tp.id_number, tp.full_name, tp.summary, tp.birthdate, tp.mobile_number, tp.photo_id, ");
		sql.append("tp.cv_id, tp.expected_salary, tg.gender_name, tms.status_name, tpt.type_name ");
		sql.append("FROM t_profile tp ");
		sql.append("INNER JOIN t_user tu ON tu.profile_id = tp.id ");
		sql.append("INNER JOIN t_gender tg ON tg.id = tp.gender_id ");
		sql.append("INNER JOIN t_marital_status tms ON tms.id = tp.marital_status_id ");
		sql.append("INNER JOIN t_person_type tpt ON tpt.id = tp.person_type_id ");
		sql.append("WHERE tu.id = :userId ");
		
		final Object profileObj = this.em().createNativeQuery(sql.toString())
				.setParameter("userId", userId)
				.getSingleResult();
		
		final Object[] profileArr = (Object[]) profileObj;
		
		Profile profile = null;
		
		if (profileArr.length > 0) {
			profile = new Profile();
			profile.setId(profileArr[0].toString());
			profile.setIdNumber(profileArr[1].toString());
			profile.setFullName(profileArr[2].toString());
			profile.setSummary(profileArr[3].toString());
			profile.setBirthdate(LocalDate.parse(profileArr[4].toString()));
			profile.setMobileNumber(profileArr[5].toString());
			final File photo = new File();
			photo.setId(profileArr[6].toString());
			profile.setPhoto(photo);
			final File cv = new File();
			cv.setId(profileArr[7].toString());
			profile.setCv(cv);
			profile.setExpectedSalary(Integer.valueOf(profileArr[8].toString()));
			final Gender gender = new Gender();
			gender.setGenderName(profileArr[9].toString());
			profile.setGender(gender);
			final MaritalStatus status = new MaritalStatus();
			status.setStatusName(profileArr[10].toString());
			profile.setMaritalStatus(status);
			final PersonType type = new PersonType();
			type.setTypeName(profileArr[11].toString());
			profile.setPersonType(type);
		}
		
		return profile;
	}
}
