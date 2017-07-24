package com.hack17.hybo.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.CorrelatedFund;
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
		return entityManager
				.createQuery("from CorrelatedFund cf where cf.ticker=:ticker",
						CorrelatedFund.class).setParameter("ticker", ticker)
				.getSingleResult().getCorrelatedTicker();
	}

}
