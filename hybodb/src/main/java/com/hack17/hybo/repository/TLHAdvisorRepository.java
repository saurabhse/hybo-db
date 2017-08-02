package com.hack17.hybo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.TLHAdvice;

@Repository
public class TLHAdvisorRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void saveTLHAdvice(TLHAdvice tlhAdvice){
		entityManager.persist(tlhAdvice);
	}
}
