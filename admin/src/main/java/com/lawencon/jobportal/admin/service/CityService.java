package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportal.admin.dao.CityDao;
import com.lawencon.jobportal.admin.dto.InsertResDto;
import com.lawencon.jobportal.admin.dto.UpdateResDto;
import com.lawencon.jobportal.admin.dto.city.CityGetResDto;
import com.lawencon.jobportal.admin.dto.city.CityInsertReqDto;
import com.lawencon.jobportal.admin.dto.city.CityUpdateReqDto;
import com.lawencon.jobportal.admin.model.City;

@Service
public class CityService {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CityDao cityDao;
	
	public InsertResDto insertCity(CityInsertReqDto data) {
		em().getTransaction().begin();
		
		final String cityCode = generateCode();
		final City city = new City();
		city.setCityCode(cityCode);
		city.setCityName(data.getCityName());
		final City cities = cityDao.save(city);
		
		final InsertResDto result = new InsertResDto();
		result.setId(cities.getId());
		result.setMessage("City added successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	public List<CityGetResDto> getAllCity(){
		final List<City> listCity = cityDao.getAll(City.class);
		final List<CityGetResDto> listResult = new ArrayList<>();
		for(City c:listCity) {
			final CityGetResDto result = new CityGetResDto();
			result.setCityId(c.getId());
			result.setCityCode(c.getCityCode());
			result.setCityName(c.getCityName());
			listResult.add(result);
		}
		return listResult;
	}
	
	
	public UpdateResDto updateCity(CityUpdateReqDto data) {
		em().getTransaction().begin();
		
		final City cityDb = cityDao.getByCode(data.getCityCode());
		final City city = cityDao.getById(City.class,cityDb.getId());
		city.setCityName(data.getCityName());
		final City cityResult = cityDao.saveAndFlush(city);
		
		final UpdateResDto result = new UpdateResDto();
		result.setVersion(cityResult.getVersion());
		result.setMessage("Data updated successfully.");
		
		em().getTransaction().commit();
		return result;
	}
	
	
	public boolean deleteCity(String cityCode) {
		em().getTransaction().begin();
		
		final City cityDb = cityDao.getByCode(cityCode);
		final Boolean deleted = cityDao.deleteById(City.class, cityDb.getId());
		
		em().getTransaction().commit();
		return deleted;
	}
}
