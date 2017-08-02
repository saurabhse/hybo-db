package com.hack17.hybo.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.Fund;

@Repository
public class FundRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	public Fund findFund(String fundTicker) {		
		return entityManager.createQuery(String.format("from Fund f where f.ticker='%s'",fundTicker),  Fund.class).getSingleResult();
	}
}
