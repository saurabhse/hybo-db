package com.hack17.hybo.util;

import static com.hack17.hybo.util.DateTimeUtil.format2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;





import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;



import org.springframework.data.convert.JodaTimeConverters.DateTimeToDateConverter;
import org.springframework.stereotype.Component;

import com.hack17.hybo.domain.Allocation;
import com.hack17.hybo.domain.CreatedBy;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.PortfolioTaxAlphaHistory;
import com.hack17.hybo.domain.Transaction;
import com.hack17.hybo.repository.PortfolioRepository;
import com.hack17.hybo.repository.ReferenceDataRepository;
import com.hack17.hybo.repository.TransactionRepository;

@Component
public class ReportUtil implements BeanFactoryAware{
	
	private static BeanFactory context;
	
	public static String format(Portfolio portfolio, Date date){
		
		StringBuilder strBld = new StringBuilder();
		strBld.append(String.format("\n\n\nPortfolio id - %d",portfolio.getId()));
		
		char [] dash = new char[50];
		Arrays.fill(dash, '-');
		Map<Allocation, Double[]> currValueMap = calculateCurrentValues(
				portfolio, date);
		strBld.append(String.format("\n%-70s\n", new String(dash)));
		strBld.append(String.format("|%-5s|%10s|%10s|%10s|%10s|", "Ticker", "Cost Price", "Quantity", "Buy Date","Current Price"));
		for(int index=0; index<portfolio.getAllocations().size();index++){
			Allocation alloc = portfolio.getAllocations().get(index);
			if("N".equals(alloc.getIsActive())){
				continue;
			}
			strBld.append(String.format("\n%-70s\n", new String(dash)));
			strBld.append(format(alloc, currValueMap.get(alloc)[0]));
		}
		
		double portfolioValue = getCurrentTotalValue(currValueMap);
		strBld.append(String.format("\n\nValue on %s - %s",format2(date),portfolioValue));
//		strBld.append(String.format("\nTax Alpha - %s", "not available"));
		return strBld.toString();
	}

	private static double getCurrentTotalValue(
			Map<Allocation, Double[]> currValueMap) {
		return currValueMap.values().stream().mapToDouble(d-> d[1]).sum();
	}

	private static Map<Allocation, Double[]> calculateCurrentValues(
			Portfolio portfolio, Date date) {
		ReferenceDataRepository refDataRepo = getRefDataRepository();
		Map<Allocation, Double[]> currValueMap = new HashMap<>();
		portfolio.getAllocations().stream().filter(alloc-> "Y".equals(alloc.getIsActive())).forEach(alloc->{
			double currVal = refDataRepo.getPriceOnDate(alloc.getFund().getTicker(), date);
			Double[] currPrices = new Double[2];
			currPrices[0]= currVal;
			currPrices[1]= currVal*alloc.getQuantity();
			currValueMap.put(alloc, currPrices);
		});
		return currValueMap;
	}

	private static ReferenceDataRepository getRefDataRepository() {
		ReferenceDataRepository refDataRepo = context.getBean(ReferenceDataRepository.class);
		return refDataRepo;
	}
	
	private static PortfolioRepository getPortfolioRepository() {
		PortfolioRepository portfolioRepo = context.getBean(PortfolioRepository.class);
		return portfolioRepo;
	}
	
	private static TransactionRepository getTransactionRepository() {
		TransactionRepository transactionRepo = context.getBean(TransactionRepository.class);
		return transactionRepo;
	}
	
	public static String format(Allocation allocation, double currentPrice){
		StringBuilder strBld = new StringBuilder();
		strBld.append(String.format("|%-5s|%10s|%10s|%10s|%10s|", allocation.getFund().getTicker(), allocation.getCostPrice(), allocation.getQuantity(), format2(allocation.getBuyDate()),currentPrice));
		return strBld.toString();
	}
	
	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		context = arg0;
		
	}

	public static void createTLHHistory(Portfolio portfolio, Date today) {
		if(getPortfolioRepository().getPortfolioTaxAlphaHistory(portfolio, today).size()!=0)
			return;
		Map<Allocation, Double[]> currValueMap = calculateCurrentValues(
				portfolio, today);
		double portfolioValue = getCurrentTotalValue(currValueMap);
		PortfolioTaxAlphaHistory taxAlphaHist = new PortfolioTaxAlphaHistory();
		taxAlphaHist.setPortfolio(portfolio);
		taxAlphaHist.setPortfolioValue(portfolioValue);
		taxAlphaHist.setAsOfDate(today);
		if(DateTimeUtil.isMonth(today, 9) && DateTimeUtil.isDay(today, 30)){
			Date fromDate = DateTimeUtil.getFinancialYearDate(DateTimeUtil.FROM, today);
			Date toDate = DateTimeUtil.getFinancialYearDate(DateTimeUtil.TO, today);
			List<Transaction> transactions = getTransactionRepository().getTransactions(portfolio, fromDate, toDate, CreatedBy.TLH);
			double tlh = transactions.stream().mapToDouble(tran->tran.getBuyPrice()*tran.getSellPrice()*tran.getQuantity()).sum();
			taxAlphaHist.setTotalTLH(tlh);
			taxAlphaHist.setTaxAlpha((tlh*40/100)/portfolioValue);
		}
		getPortfolioRepository().persist(taxAlphaHist);
	}
}
