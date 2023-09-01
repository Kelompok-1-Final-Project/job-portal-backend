package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Relationship;

@Repository
public class RelationshipDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public Relationship getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, relationship_code, relationship_name, ver ");
		sql.append("FROM t_relationship ");
		sql.append("WHERE relationship_code = :code ");

		final Object relationshipObj = em().createNativeQuery(sql.toString())
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] relationshipArr = (Object[]) relationshipObj;
		
		Relationship relationship = null;
		
		if (relationshipArr.length > 0) {
			relationship = new Relationship();
			relationship.setId(relationshipArr[0].toString());
			relationship.setRelationshipCode(relationshipArr[1].toString());
			relationship.setRelationshipName(relationshipArr[2].toString());
			relationship.setVersion(Integer.valueOf(relationshipArr[3].toString()));
		}
		
		return relationship;
	}
}
