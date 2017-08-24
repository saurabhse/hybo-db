package com.hack17.hybo.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.TLHAdvice;


@Repository
public class TLHAdvisorRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void saveTLHAdvice(TLHAdvice tlhAdvice){
		entityManager.persist(tlhAdvice);
	}
	
	public List<TLHAdvice> findTLHAdviceInDateRange(Portfolio portfolio, Date fromDate, Date toDate){
		return entityManager.createQuery("from TLHAdvice tlha where tlha.portfolio=:portfolio and cast(tlha.advisedOnDate as date) between :fromDate and :toDate", TLHAdvice.class)
		.setParameter("portfolio", portfolio)
		.setParameter("fromDate", fromDate)
		.setParameter("toDate", toDate)
		.getResultList();
	}
	
	public List<TLHAdvice> findTLHAdviceOnDate(Portfolio portfolio, Date date){
		return entityManager.createQuery("from TLHAdvice tlha where tlha.portfolio=? and cast(tlha.advisedOnDate as date) = ?", TLHAdvice.class)
		.setParameter(1, portfolio)
		.setParameter(2, date)
		.getResultList();
	}
	
	
}
