package com.lawencon.jobportal.admin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.model.City;
import com.lawencon.jobportal.admin.model.Company;
import com.lawencon.jobportal.admin.model.File;
import com.lawencon.jobportal.admin.model.Industry;

@Repository
public class CompanyDao extends AbstractJpaDao{
	
	private final EntityManager em = ConnHandler.getManager();

	public Company getByCode(String companyCode) {
		final String sql = "SELECT "
				+ "c.id, c.company_name, c.company_code, c.ver, ci.city_name, c.file_id, i.industry_name "
				+ "FROM "
				+ "t_company co "
				+ "INNER JOIN "
				+ "t_city ci "
				+ "ON ci.id = co.city_id "
				+ "INNER JOIN "
				+ "t_industry i "
				+ "ON i.id = co.industry_id "
				+ "WHERE "
				+ "company_code = :companyCode";
		
		final Object companyObj = this.em.createNamedQuery(sql)
				.setParameter("companyCode", companyCode)
				.getSingleResult();
		
		final Object[] companyArr = (Object[]) companyObj;
		
		Company company = null;
		
		if(companyArr.length > 0) {
			company = new Company();
			company.setId(companyArr[0].toString());
			company.setCompanyName(companyArr[1].toString());
			company.setCompanyCode(companyArr[2].toString());
			company.setVersion(Integer.valueOf(companyArr[3].toString()));
			
			final City city = new City();
			city.setCityName(companyArr[4].toString());
			company.setCity(city);
			
			final File file = new File();
			file.setId(companyArr[5].toString());
			company.setFile(file);
			
			final Industry industry = new Industry();
			industry.setIndustryName(companyArr[6].toString());
			company.setIndustry(industry);
		}
		
		return company;
	}
}
