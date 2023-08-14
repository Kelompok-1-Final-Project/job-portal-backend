package com.lawencon.jobportal.candidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.candidate.dao.CityDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.city.CityGetResDto;
import com.lawencon.jobportal.candidate.dto.city.CityInsertReqDto;
import com.lawencon.jobportal.candidate.model.City;

@Service
public class CityService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CityDao cityDao;

	public InsertResDto insertCity(CityInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();

			final City city = new City();
			city.setCityCode(data.getCityCode());
			city.setCityName(data.getCityName());
			final City cities = cityDao.save(city);

			result.setId(cities.getId());
			result.setMessage("City added successfully.");

			result.setId(cities.getId());
			result.setMessage("City added successfully.");
			
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return result;
	}

	public List<CityGetResDto> getAllCity() {
		final List<City> listCity = cityDao.getAll(City.class);
		final List<CityGetResDto> listResult = new ArrayList<>();
		for (City c : listCity) {
			final CityGetResDto result = new CityGetResDto();
			result.setCityId(c.getId());
			result.setCityCode(c.getCityCode());
			result.setCityName(c.getCityName());
			listResult.add(result);
		}
		return listResult;
	}

}
