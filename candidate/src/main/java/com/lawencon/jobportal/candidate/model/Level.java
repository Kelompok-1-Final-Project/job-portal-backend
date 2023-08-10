package com.lawencon.jobportal.candidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name = "t_level")
public class Level extends BaseEntity{
	
	@Column(name = "level_code",length =5,unique=true,nullable = false)
	private String levelCode;
	
	@Column(name = "level_name",length =36,nullable = false)
	private String levelName;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
}
