package com.hack17.hybo.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.CreatedBy;
import com.hack17.hybo.domain.Fund;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.Transaction;

@Repository
public class TransactionRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Transaction> getTransactions(Fund fund, Portfolio portfolio, Date fromDate, Date toDate){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where fund=? and portfolio=? and sellDate between ? and ?", Transaction.class); 
		query.setParameter(1, fund);
		query.setParameter(2, portfolio);
		query.setParameter(3, fromDate);
		query.setParameter(4, toDate);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(Portfolio portfolio, Date fromDate, Date toDate){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where portfolio=? and sellDate between ? and ?", Transaction.class); 
		query.setParameter(1, portfolio);
		query.setParameter(2, fromDate);
		query.setParameter(3, toDate);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(Portfolio portfolio, Date fromDate, Date toDate, CreatedBy createdBy){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where portfolio=? and sellDate between ? and ? and createdBy=?", Transaction.class); 
		query.setParameter(1, portfolio);
		query.setParameter(2, fromDate);
		query.setParameter(3, toDate);
		query.setParameter(4, createdBy);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(Fund fund, Portfolio portfolio){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where fund=? and portfolio=?", Transaction.class); 
		query.setParameter(1, fund);
		query.setParameter(2, portfolio);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction", Transaction.class); 
		return query.getResultList();
	}
}
