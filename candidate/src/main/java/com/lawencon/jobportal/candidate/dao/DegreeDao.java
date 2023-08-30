package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.Degree;

@Repository
public class DegreeDao extends AbstractJpaDao {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Degree getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, degree_code, degree_name, ver ");
		sql.append("FROM t_degree ");
		sql.append("WHERE degree_code = :code ");

		final Object degreeObj = this.em().createNativeQuery(sql.toString())
				.setParameter("code", code)
				.getSingleResult();

		final Object[] degreeArr = (Object[]) degreeObj;

		Degree degree = null;

		if (degreeArr.length > 0) {
			degree = new Degree();
			degree.setId(degreeArr[0].toString());
			degree.setDegreeCode(degreeArr[1].toString());
			degree.setDegreeName(degreeArr[2].toString());
			degree.setVersion(Integer.valueOf(degreeArr[3].toString()));
		}

		return degree;
	}
}
