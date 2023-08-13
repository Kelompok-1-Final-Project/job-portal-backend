package com.lawencon.jobportal.candidate.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Degree;
import com.lawencon.jobportal.candidate.model.Family;
import com.lawencon.jobportal.candidate.model.Relationship;

@Repository
public class FamilyDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Family> getByCandidate(String candidateId){
		final List<Family> families = new ArrayList<>();
		
		final String sql = "SELECT "
				+ "tf.id, tf.family_name, tr.relationship_name, tf.family_degree, tf.family_birthdate, tf.ver "
				+ "FROM "
				+ "t_family tf "
				+ "INNER JOIN "
				+ "t_relationship tr "
				+ "ON "
				+ "tf.relationship_id = tr.id "
				+ "WHERE "
				+ "candidate_id = :candidateId ";
		
		final List<?> familyObjs = em().createNativeQuery(sql)
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		if (familyObjs.size() > 0) {
			for (Object familyObj : familyObjs) {
				final Object[] familyArr = (Object[]) familyObj;

				final Family family = new Family();
				family.setId(familyArr[0].toString());
				family.setFamilyName(familyArr[1].toString());
				
				final Relationship relationship = new Relationship();
				relationship.setRelationshipName(familyArr[2].toString());
				family.setRelationship(relationship);
				
				final Degree degree = new Degree();
				degree.setDegreeName(familyArr[3].toString());
				family.setFamilyDegree(degree);
				
				family.setFamilyBirthdate(LocalDate.parse(familyArr[4].toString()));
				family.setVersion(Integer.valueOf(familyArr[5].toString()));
				
				families.add(family);
			}
		}

		return families;

	}
}
