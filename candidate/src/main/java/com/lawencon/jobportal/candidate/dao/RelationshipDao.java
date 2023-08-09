package com.lawencon.jobportal.candidate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class RelationshipDao extends AbstractJpaDao{
	@PersistenceContext
	private EntityManager em;
	
	
}
