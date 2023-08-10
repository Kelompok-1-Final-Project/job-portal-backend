package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;

public class City {

	@Column(name = "city_code",length =5,unique=true,nullable = false)
	private String cityCode;
	
	@Column(name = "city_name",length =30,nullable = false)
	private String cityName;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	
}
