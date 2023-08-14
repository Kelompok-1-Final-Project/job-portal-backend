package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.Industry;

@Repository
public class IndustryDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public Industry getByCode(String code) {
		final String sql = "SELECT "
				+ "id, industry_name, industry_code, ver "
				+ "FROM " 
				+ "t_industry " 
				+ "WHERE "
				+ "industry_code = :code";

		final Object industryObj = this.em().createNativeQuery(sql)
				.setParameter("code", code)
				.getSingleResult();

		final Object[] industryArr = (Object[]) industryObj;

		Industry industry = null;

		if (industryArr.length > 0) {
			industry = new Industry();
			industry.setId(industryArr[0].toString());
			industry.setIndustryName(industryArr[1].toString());
			industry.setIndustryCode(industryArr[2].toString());
			industry.setVersion(Integer.valueOf(industryArr[3].toString()));
		}

		return industry;
	}
	
	public Industry getByName(String industryName) {
		final String sql = "SELECT "
				+ "id, industry_name, industry_code, ver "
				+ "FROM " 
				+ "t_industry " 
				+ "WHERE "
				+ "industry_name ILIKE :industryName";

		try {
			final Object industryObj = this.em().createNativeQuery(sql)
					.setParameter("industryName", industryName)
					.getSingleResult();

			final Object[] industryArr = (Object[]) industryObj;

			Industry industry = null;

			if (industryArr.length > 0) {
				industry = new Industry();
				industry.setId(industryArr[0].toString());
				industry.setIndustryName(industryArr[1].toString());
				industry.setIndustryCode(industryArr[2].toString());
				industry.setVersion(Integer.valueOf(industryArr[3].toString()));
			}

			return industry;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
