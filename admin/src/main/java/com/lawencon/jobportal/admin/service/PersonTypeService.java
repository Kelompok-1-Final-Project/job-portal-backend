package com.lawencon.jobportal.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.admin.dao.PersonTypeDao;
import com.lawencon.jobportal.admin.dto.persontype.PersonTypeGetResDto;
import com.lawencon.jobportal.admin.model.PersonType;

@Service
public class PersonTypeService {
	
	@Autowired
	private PersonTypeDao personTypeDao;
	
	public List<PersonTypeGetResDto> getAll() {
		final List<PersonType> listPersonType = personTypeDao.getAll(PersonType.class);
		final List<PersonTypeGetResDto> listResult = new ArrayList<>();
		for(PersonType pt: listPersonType) {
			final PersonTypeGetResDto personTypeGetResDto = new PersonTypeGetResDto();
			personTypeGetResDto.setId(pt.getId());
			personTypeGetResDto.setTypeCode(pt.getTypeCode());
			personTypeGetResDto.setTypeName(pt.getTypeName());
			listResult.add(personTypeGetResDto);
		}
		return listResult;
	}
}
