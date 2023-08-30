package com.lawencon.jobportal.candidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.model.City;

@Repository
public class CityDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<City> getByCityName(String cityName) {
		final List<City> cities = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, city_name, city_code, ver ");
		sql.append("FROM t_city ");
		sql.append("WHERE city_name ILIKE ':cityName%' ");
		
		final List<?> cityObjs = this.em().createNativeQuery(sql.toString())
				.setParameter("cityName", cityName)
				.getResultList();

		if (cityObjs.size() > 0) {
			for (Object cityObj : cityObjs) {
				final Object[] cityArr = (Object[]) cityObj;

				final City city = new City();
				city.setId(cityArr[0].toString());
				city.setCityName(cityArr[1].toString());
				city.setCityCode(cityArr[2].toString());
				city.setVersion(Integer.valueOf(cityArr[3].toString()));
				cities.add(city);
			}
		}

		return cities;
	}

	public City getByCode(String cityCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, city_name, city_code, ver ");
		sql.append("FROM t_city ");
		sql.append("WHERE city_code = :cityCode ");
		
		final Object cityObj = this.em().createNativeQuery(sql.toString())
				.setParameter("cityCode", cityCode)
				.getSingleResult();
		
		final Object[] cityArr = (Object[]) cityObj;
		
		City city = null;
		
		if(cityArr.length > 0) {
			city = new City();
			city.setId(cityArr[0].toString());
			city.setCityName(cityArr[1].toString());
			city.setCityCode(cityArr[2].toString());
			city.setVersion(Integer.valueOf(cityArr[3].toString()));
		}
		
		return city;
	}

}
