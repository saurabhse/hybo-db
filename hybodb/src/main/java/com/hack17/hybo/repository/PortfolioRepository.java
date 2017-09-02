package com.hack17.hybo.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.IndexPrice;
import com.hack17.hybo.domain.InvestorProfile;
import com.hack17.hybo.domain.MarketStatus;
import com.hack17.hybo.domain.MarketWeight;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.PortfolioTaxAlphaHistory;
import com.hack17.hybo.domain.TLHRunPortfolioHistory;
import com.hack17.hybo.domain.Transaction;
import com.hack17.hybo.domain.UserClientMapping;

@Repository
public class PortfolioRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	public Object getEntity(int id,Class object){
		return entityManager.find(object, id);
	}
	public Object getEntityForAny(Class object,Object key){
		return entityManager.find(object, key);
	}
	public Portfolio getPortfolio(long portfolioId){
		return entityManager.find(Portfolio.class, portfolioId);
	}
	
	public List<Portfolio> getAllPortfolios(){
		return entityManager.createQuery("from Portfolio", Portfolio.class).getResultList();
	}
	public List<IndexPrice> getAllIndexPrice(){
		return entityManager.createQuery("from IndexPrice", IndexPrice.class).getResultList();
	}
	public List<Transaction> getAllTransactions(){
		return entityManager.createQuery("from Transaction", Transaction.class).getResultList();
	}
	public List<InvestorProfile> getAllInvestorProfile(){
		return entityManager.createQuery("from InvestorProfile", InvestorProfile.class).getResultList();
	}
	public List<UserClientMapping> getAllUsers(){
		return entityManager.createQuery("from UserClientMapping", UserClientMapping.class).getResultList();
	}
	public List<Portfolio> getAllPortfoliosBeforeDate(Date givenDate){
		TypedQuery<Portfolio> query = entityManager.createQuery("from Portfolio where transactionDate<=:givenDate", Portfolio.class);
		query.setParameter("givenDate", givenDate,TemporalType.TIMESTAMP);
		return query.getResultList();
	}
	public List<Portfolio> getPortfolioBeforeDate(int portfolioid,Date givenDate){
		TypedQuery<Portfolio> query = entityManager.createQuery("from Portfolio where clientId=:clientId and transactionDate<=:givenDate", Portfolio.class);
		query.setParameter("clientId", portfolioid);
		query.setParameter("givenDate", givenDate,TemporalType.TIMESTAMP);
		return query.getResultList();
	}

	public List<MarketWeight> getMarketWeight(int year){
		TypedQuery<MarketWeight> query = entityManager.createQuery("from MarketWeight where year=:year",MarketWeight.class);
		query.setParameter("year", year);
		return query.getResultList();
	}
	@Transactional
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
		query.setParameter("date", date,TemporalType.TIMESTAMP);
		return query.getResultList();
	}
	public List<Portfolio> getPortfolio(int clientId){
		TypedQuery<Portfolio> query = entityManager.createQuery("from Portfolio where clientId=:clientId",Portfolio.class);
		query.setParameter("clientId", clientId);
		return query.getResultList();
	}
	public double getIndexPriceForGivenDate(String ticker,Date date){
		double price = 0d;
		TypedQuery<IndexPrice> query = entityManager.createQuery("from IndexPrice where index=:ticker and date=:givenDate",IndexPrice.class);
		query.setParameter("ticker", ticker);
		query.setParameter("givenDate", date,TemporalType.TIMESTAMP);
		List<IndexPrice> list = query.getResultList();
		if(list == null || list.size()==0){
			 query = entityManager.createQuery("from IndexPrice where index=:ticker and date <:givenDate order by date desc",IndexPrice.class);
			 query.setParameter("ticker", ticker);
			 query.setParameter("givenDate", date,TemporalType.TIMESTAMP);
			 list = query.getResultList();
			 price = list.get(0).getPrice();
		}else{
			 price = list.get(0).getPrice();
		}
		return price;
	}
	
	public List<PortfolioTaxAlphaHistory> getPortfolioTaxAlphaHistory(Portfolio portfolio, Date date){
		TypedQuery<PortfolioTaxAlphaHistory> query = entityManager.createQuery("from PortfolioTaxAlphaHistory where portfolio=? and asOfDate=?",PortfolioTaxAlphaHistory.class);
		query.setParameter(1, portfolio);
		query.setParameter(2, date);
		return query.getResultList();
	}
	
	public List<TLHRunPortfolioHistory> getTLHRunHistory(long portfolioId){
		TypedQuery<TLHRunPortfolioHistory> query = entityManager.createQuery("from TLHRunPortfolioHistory where portfolioId=?",TLHRunPortfolioHistory.class);
		query.setParameter(1, portfolioId);
		return query.getResultList();
	}
	
	
}
