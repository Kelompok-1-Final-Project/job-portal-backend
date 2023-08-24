package com.lawencon.jobportal.candidate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.candidate.dao.FileDao;
import com.lawencon.jobportal.candidate.model.File;

@Service
public class FileService {
	
	@Autowired
	private FileDao fileDao;
	
	public File getById(String id) {
		final File file = fileDao.getById(File.class, id);
		return file;
	}
}
