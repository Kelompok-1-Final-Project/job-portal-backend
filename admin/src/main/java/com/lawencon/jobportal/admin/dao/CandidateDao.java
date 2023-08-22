package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Candidate;
import com.lawencon.jobportal.admin.model.CandidateProfile;
import com.lawencon.jobportal.admin.model.Gender;
import com.lawencon.jobportal.admin.model.PersonType;

@Repository
public class CandidateDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Candidate getByName(String candidateName) {
		final String sql = "SELECT "
				+ "	tc.id, tc.email, tcp.full_name, tcp.mobile_number, tpt.type_name, tg.gender_name, tc.ver "
				+ "FROM  "
				+ "	t_candidate_profile tcp  "
				+ "INNER JOIN  "
				+ "	t_candidate tc ON tcp.id = tc.profile_id  "
				+ "INNER JOIN  "
				+ "	t_person_type tpt ON tpt.id = tcp.person_type_id "
				+ "INNER JOIN "
				+ "	t_gender tg ON tg.id = tcp.gender_id;   "
				+ "WHERE  "
				+ "	tcp.full_name ILIKE :candidateName || '%";
		
		final Object candidateObj = this.em().createNativeQuery(sql, Candidate.class)
				.setParameter("candidateName", candidateName)
				.getSingleResult();
		
		final Object[] candidateArr = (Object[]) candidateObj;
		
		Candidate candidate = null;
		if(candidateArr.length > 0) {
			candidate = new Candidate();
			candidate.setId(candidateArr[0].toString());
			candidate.setEmail(candidateArr[1].toString());
			
			final CandidateProfile candidateProfile = new CandidateProfile();
			candidateProfile.setFullName(candidateArr[2].toString());
			candidateProfile.setMobileNumber(candidateArr[3].toString());
			
			final PersonType personType = new PersonType();
			personType.setTypeName(candidateArr[4].toString());
			candidateProfile.setPersonType(personType);
			
			final Gender gender = new Gender();
			gender.setGenderName(candidateArr[5].toString());
			candidateProfile.setGender(gender);
			candidate.setCandidateProfile(candidateProfile);
			candidate.setVersion(Integer.valueOf(candidateArr[6].toString()));
		}
		
		return candidate;
	}
	
	public Candidate getByEmail(String userEmail) {
		final String sql = "SELECT "
				+ "	tc.id, tc.email, tcp.full_name, tcp.mobile_number, tc.ver "
				+ "FROM  "
				+ "	t_candidate_profile tcp  "
				+ "INNER JOIN  "
				+ "	t_candidate tc ON tcp.id = tc.profile_id  "
				+ "WHERE "
				+ "tc.email = :userEmail ";
		try {
			final Object userObj = em().createNativeQuery(sql)
					.setParameter("userEmail", userEmail)
					.getSingleResult();
			
			final Object[] candidateArr = (Object[]) userObj;
			
			Candidate candidate = null;

			if(candidateArr.length > 0) {
				candidate = new Candidate();
				candidate.setId(candidateArr[0].toString());
				candidate.setEmail(candidateArr[1].toString());
				
				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullName(candidateArr[2].toString());
				candidateProfile.setMobileNumber(candidateArr[3].toString());					
				
				candidate.setVersion(Integer.valueOf(candidateArr[4].toString()));

			}
			return candidate;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
