package com.lawencon.jobportal.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.FileDao;
import com.lawencon.jobportal.admin.model.File;

@Service
public class FileService {
	
	@Autowired
	private FileDao fileDao;
	
	public File getById(Long id) {
		final File file = fileDao.getById(File.class, id);
		return file;
	}
}
