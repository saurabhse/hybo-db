package com.hack17.hybo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.hack17.hybo.domain.Action;
import com.hack17.hybo.domain.CreatedBy;
import com.hack17.hybo.domain.Fund;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.Transaction;
import com.hack17.hybo.util.DateTimeUtil;

@Repository
public class TransactionRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ReferenceDataRepository refDataRepo;
	
	public List<Transaction> getTransactions(Fund fund, Portfolio portfolio, Date fromDate, Date toDate){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where fund=? and portfolio=? and sellDate between ? and ?", Transaction.class); 
		query.setParameter(1, fund);
		query.setParameter(2, portfolio);
		query.setParameter(3, fromDate);
		query.setParameter(4, toDate);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(Fund fund, Portfolio portfolio, Date fromDate, Date toDate, Action action){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where fund=? and portfolio=? and sellDate between ? and ? and action=?", Transaction.class); 
		query.setParameter(1, fund);
		query.setParameter(2, portfolio);
		query.setParameter(3, fromDate);
		query.setParameter(4, toDate);
		query.setParameter(5, action);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(Portfolio portfolio, Date buySellDate, CreatedBy createdBy){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where portfolio=? and createdBy=? and (sellDate = ? or buyDate = ?)", Transaction.class); 
		query.setParameter(1, portfolio);
		query.setParameter(2, createdBy);
		query.setParameter(3, buySellDate);
		query.setParameter(4, buySellDate);
		return query.getResultList();
	}
	
	public List<Transaction> getTransactions(Portfolio portfolio, Date buySellDate){
		TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where portfolio=? and (sellDate = ? or buyDate = ?)", Transaction.class); 
		query.setParameter(1, portfolio);
		query.setParameter(2, buySellDate);
		query.setParameter(3, buySellDate);
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
	
	public List<String> explainTransactions(String question){
		//explain transactions on 30-Sep-2014 in portfolio id 56435
		
		List<String> explaination = new ArrayList<String>();
		String[] tokens = question.split(" ");
		if(tokens.length !=8){
			explaination.add("Ask you query in format 'explain transactions on 30-Sep-2014 in portfolio id 56435'");
			return explaination;
		}
		
		Portfolio portfolio = entityManager.find(Portfolio.class, new Long(tokens[7]));
		if(portfolio == null){
			explaination.add(String.format("No Portfolio found for id '%s'", tokens[7]));
			return explaination;
		}
		Date date = null;
		try{
			date = DateTimeUtil.getDatedd_MMM_yyyy(tokens[3]);
		}catch(RuntimeException e){
			explaination.add(String.format("Check the date format it should be dd-MMM-yyyy e.g. 30-Sep-2014"));
			return explaination;
		}
		List<Transaction> transactions = getTransactions(portfolio, date);
		if(transactions.size()==0){
			explaination.add(String.format("No transactions found!"));
			return explaination;
		}
		transactions.forEach(tran->{
			if(CreatedBy.TLH.equals(tran.getCreatedBy())){
				StringBuilder response = new StringBuilder();
				String correlatedFund = refDataRepo.getCorrelatedTicker(tran.getFund().getTicker());
				response.append(tran.getAction().equals(Action.SELL)?"Sold ":"Bought ");
				if(tran.getAction().equals(Action.SELL)){
					response.append(String.format("%s quantity of %s at price %s", tran.getQuantity(), tran.getFund().getTicker(), tran.getSellPrice()));
					response.append(String.format(" for the purpose of tax loss harvesting."));
				}else{
					response.append(String.format("%s quantity of %s at price %s", tran.getQuantity(), tran.getFund().getTicker(), tran.getBuyPrice()));
					response.append(String.format(" which is correlated fund of %s", correlatedFund));
				}
				
				explaination.add(response.toString());
			}
			
				
		});
		return explaination;
	}
}
