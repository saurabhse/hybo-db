package com.hack17.hybo.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.CorrelatedFund;
import com.hack17.hybo.domain.Fund;
import com.hack17.hybo.domain.SecurityPrice;

@Repository
public class ReferenceDataRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public double getLatestPrice(String ticker) {
		return entityManager
				.createQuery(
						"from SecurityPrice sp where sp.ticker=:ticker order by sp.priceDate desc",
						SecurityPrice.class).setParameter("ticker", ticker).getResultList().get(0).getPrice();
	}

	public double getPriceOnDate(String ticker, Date priceDate) {
		List<SecurityPrice> securityPrices = entityManager
				.createQuery(
						"from SecurityPrice sp where sp.ticker=:ticker and cast(sp.priceDate as date) = :priceDate",
						SecurityPrice.class)
						.setParameter("ticker", ticker)
				.setParameter("priceDate", priceDate).getResultList(); 
		if(securityPrices.size()==0)
			return 0d;
		
		return securityPrices.get(0).getPrice();
	}

	public String getCorrelatedTicker(String ticker) {
		String alternateTicker = null;
		try{
			alternateTicker = entityManager
			.createQuery("from CorrelatedFund cf where cf.ticker=:ticker",
					CorrelatedFund.class).setParameter("ticker", ticker)
			.getSingleResult().getCorrelatedTicker();
		}catch(Exception ex){
			return alternateTicker;
		}
		return alternateTicker;
	}
	
	@Transactional
	public void createPrice(String ticker, double price, Date priceDate){
		SecurityPrice secPrice = new SecurityPrice();
		secPrice.setTicker(ticker);
		secPrice.setPrice(price);
		secPrice.setPriceDate(priceDate);
		entityManager.persist(secPrice);
	}
	
	@Transactional
	public void createFund(String ticker){
		Fund fund = new Fund();
		fund.setTicker(ticker);
		entityManager.persist(fund);
	}
	
	@Transactional
	public void createCorrelatedFund(String ticker, String correlatedTicker){
		CorrelatedFund correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker(ticker);
		correlatedFund.setCorrelatedTicker(correlatedTicker);
		entityManager.persist(correlatedFund);
		//create opposite mapping as well
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker(correlatedTicker);
		correlatedFund.setCorrelatedTicker(ticker);
		entityManager.persist(correlatedFund);
	}
	
	public void deleteAllPrices(){
		entityManager.createQuery("delete from SecurityPrice").executeUpdate();
	}
	
	public void deleteAllCorrelatedFunds(){
		entityManager.createQuery("delete from CorrelatedFund").executeUpdate();
	}
	
	public <T> List<T> getAll(Class<T> clazz){
		String query = String.format("select t from %s t",clazz.getSimpleName());
		return entityManager.createQuery(query).getResultList();
	}

}
