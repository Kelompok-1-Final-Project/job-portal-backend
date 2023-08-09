package com.lawencon.jobportal.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_file")
public class File {
	
	@Column(name = "ext", length = 5, nullable = false)
	private String ext;
	
	@Column(name = "file", nullable = false)
	private String file;

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
