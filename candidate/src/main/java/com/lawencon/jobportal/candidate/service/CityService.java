package com.lawencon.jobportal.candidate.service;

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

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportal.candidate.dao.CityDao;
import com.lawencon.jobportal.candidate.dto.InsertResDto;
import com.lawencon.jobportal.candidate.dto.UpdateResDto;
import com.lawencon.jobportal.candidate.dto.benefit.CityUpdateReqDto;
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
	
	public UpdateResDto updateCity(CityUpdateReqDto data) {
		final UpdateResDto result = new UpdateResDto();
		try {
			em().getTransaction().begin();
			
			final City cityDb = cityDao.getByCode(data.getCityCode());
			final City city = cityDao.getById(City.class,cityDb.getId());
			city.setCityName(data.getCityName());
			final City cityResult = cityDao.save(city);
			
			final String updateCityCandidateAPI = "http://localhost:8081/cities";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CityUpdateReqDto> cityUpdate = RequestEntity.patch(updateCityCandidateAPI).headers(headers)
					.body(data);

			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(cityUpdate, UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				result.setVersion(cityResult.getVersion());
				result.setMessage("City updated successfully.");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}
			
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}

}
