package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Relationship;

@Repository
public class RelationshipDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public Relationship getByCode(String code) {
		final String sql = "SELECT "
				+ "id, relationship_name, relationship_code, ver " 
				+ "FROM " 
				+ "t_Relationship " 
				+ "WHERE "
				+ "relationship_code = :code";

		final Object relationshipObj = this.em().createNativeQuery(sql)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] relationshipArr = (Object[]) relationshipObj;

		Relationship relationship = null;

		if (relationshipArr.length > 0) {
			relationship = new Relationship();
			relationship.setId(relationshipArr[0].toString());
			relationship.setRelationshipName(relationshipArr[1].toString());
			relationship.setRelationshipCode(relationshipArr[2].toString());
			relationship.setVersion(Integer.valueOf(relationshipArr[3].toString()));
		}

		return relationship;
	}
}
