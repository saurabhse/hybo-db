package com.hack17.hybo.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.IndexPrice;
import com.hack17.hybo.domain.MarketStatus;
import com.hack17.hybo.domain.MarketWeight;
import com.hack17.hybo.domain.Portfolio;

@Repository
public class PortfolioRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	public Object getEntity(long id,Class oject){
		return entityManager.find(Object.class, id);
	}
	public Portfolio getPortfolio(long portfolioId){
		return entityManager.find(Portfolio.class, portfolioId);
	}
	
	public List<Portfolio> getAllPortfolios(){
		return entityManager.createQuery("from Portfolio", Portfolio.class).getResultList();
	}
	
	public List<MarketWeight> getMarketWeight(int year){
		TypedQuery<MarketWeight> query = entityManager.createQuery("from MarketWeight where year=:year",MarketWeight.class);
		query.setParameter("year", year);
		return query.getResultList();
	}
	public void persist(Object portfolio){
		entityManager.persist(portfolio);
	}
	public void merge(Object portfolio){
		entityManager.merge(portfolio);
	}
	public EntityTransaction getTransaction(){
		return entityManager.getTransaction();
	}
	
	public void delete(Object object){
		entityManager.remove(object);
	}
	public List<IndexPrice> getIndexPrice(String index,Date date){
		TypedQuery<IndexPrice> query = entityManager.createQuery("from IndexPrice where index=:index and date<:date",IndexPrice.class);
		query.setParameter("index", index);
		query.setParameter("date", date);
		return query.getResultList();
	}
	public List<Portfolio> getPortfolio(int clientId){
		TypedQuery<Portfolio> query = entityManager.createQuery("from Portfolio where clientId=:clientId",Portfolio.class);
		query.setParameter("clientId", clientId);
		return query.getResultList();
	}
}
