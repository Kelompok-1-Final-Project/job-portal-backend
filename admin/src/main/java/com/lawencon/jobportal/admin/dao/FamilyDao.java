package com.lawencon.jobportal.admin.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Degree;
import com.lawencon.jobportal.admin.model.Family;
import com.lawencon.jobportal.admin.model.Relationship;

@Repository
public class FamilyDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<Family> getAllFamilyCandidate(String candidateId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tf.id, tf.family_name, tr.relationship_code, tr.relationship_name, ");
		sql.append("td.degree_code, td.degree_name, tf.birthdate ");
		sql.append("FROM t_family tf ");
		sql.append("INNER JOIN t_degree td ON td.id = tf.degree_id ");
		sql.append("INNER JOIN t_relationship tr ON tr.id = tf.relationship_id ");
		sql.append("WHERE candidate_id = :candidateId ");
		
		final List<?> familyObj = this.em().createNativeQuery(sql.toString())
				.setParameter("candidateId", candidateId)
				.getResultList();
		
		final List<Family> listFamily = new ArrayList<>();
		if(familyObj.size()>0) {
			for(Object obj:familyObj) {
				final Object[] familyArr = (Object[]) obj;
				final Family family = new Family();
				family.setId(familyArr[0].toString());
				family.setFamilyName(familyArr[1].toString());
				final Relationship relationship = new Relationship();
				relationship.setRelationshipCode(familyArr[2].toString());
				relationship.setRelationshipName(familyArr[3].toString());
				family.setRelationship(relationship);
				final Degree degree = new Degree();
				degree.setDegreeCode(familyArr[4].toString());
				degree.setDegreeName(familyArr[5].toString());
				family.setDegree(degree);
				family.setBirthdate(LocalDate.parse(familyArr[6].toString()));
				listFamily.add(family);
			}
		}
		return listFamily;
	}
}
