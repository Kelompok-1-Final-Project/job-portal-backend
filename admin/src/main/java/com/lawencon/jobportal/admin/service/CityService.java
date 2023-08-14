package com.lawencon.jobportal.admin.service;

import static com.lawencon.jobportal.admin.util.GeneratorId.generateCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
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
	
	@Autowired
	private RestTemplate restTemplate;
	
	public InsertResDto insertCity(CityInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			
			final String cityCode = generateCode();
			data.setCityCode(cityCode);
			final City city = new City();
			city.setCityCode(cityCode);
			city.setCityName(data.getCityName());
			final City cities = cityDao.save(city);
			
			result.setId(cities.getId());
			result.setMessage("City added successfully.");
			
			final String cityInsertCandidateAPI = "http://localhost:8081/cities";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CityInsertReqDto> cityInsert = RequestEntity.post(cityInsertCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(cityInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(cities.getId());
				result.setMessage("City added successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
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
