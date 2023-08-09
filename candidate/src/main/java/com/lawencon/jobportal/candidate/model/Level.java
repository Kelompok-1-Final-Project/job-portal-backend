package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name = "t_level")
public class Level extends BaseEntity{
	
	@Column(name = "level_name",length =36,nullable = false)
	private String genderName;

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

}
