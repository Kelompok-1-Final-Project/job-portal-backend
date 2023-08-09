package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

@Repository
public class UserDao extends AbstractJpaDao{
	private EntityManager em = ConnHandler.getManager();
	
}
