package com.hack17.hybo.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hack17.hybo.domain.Action;
import com.hack17.hybo.domain.Allocation;
import com.hack17.hybo.domain.CreatedBy;
import com.hack17.hybo.domain.Transaction;

@Service
public class DBLoggerService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void logTransaction(Allocation allocation, double sellPrice, Date sellDate, double sellQuantity, Action action){
		Transaction transaction = new Transaction();
		transaction.setPortfolio(allocation.getPortfolio());
		transaction.setFund(allocation.getFund());
		transaction.setBuyDate(allocation.getBuyDate());
		transaction.setBuyPrice(allocation.getCostPrice());
		transaction.setSellDate(sellDate);
		transaction.setSellPrice(sellPrice);
		transaction.setQuantity(sellQuantity);
		transaction.setAction(action);
		entityManager.persist(transaction);
	}
	
	@Transactional
	public void logTransaction(Allocation allocation, double sellPrice, Date sellDate, double sellQuantity, Action action, CreatedBy createdBy){
		Transaction transaction = new Transaction();
		transaction.setPortfolio(allocation.getPortfolio());
		transaction.setFund(allocation.getFund());
		transaction.setBuyDate(allocation.getBuyDate());
		transaction.setBuyPrice(allocation.getRebalanceDayPrice());
		transaction.setSellDate(sellDate);
		transaction.setSellPrice(sellPrice);
		transaction.setQuantity(sellQuantity);
		transaction.setAction(action);
		transaction.setCreatedBy(createdBy);
		entityManager.persist(transaction);
	}
}
