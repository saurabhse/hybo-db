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



import org.springframework.stereotype.Component;

import com.hack17.hybo.domain.Allocation;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.repository.ReferenceDataRepository;

@Component
public class ReportUtil implements BeanFactoryAware{
	
	private static BeanFactory context;
	
	public static String format(Portfolio portfolio, Date date){
		ReferenceDataRepository refDataRepo = context.getBean(ReferenceDataRepository.class);
		StringBuilder strBld = new StringBuilder();
		strBld.append(String.format("\n\n\nPortfolio id - %d",portfolio.getId()));
		
		char [] dash = new char[50];
		Arrays.fill(dash, '-');
		Map<String, Double[]> currValueMap = new HashMap<>();
		portfolio.getAllocations().forEach(alloc->{
			double currVal = refDataRepo.getPriceOnDate(alloc.getFund().getTicker(), date);
			Double[] currPrices = new Double[2];
			currPrices[0]= currVal;
			currPrices[1]= currVal*alloc.getQuantity();
			currValueMap.put(alloc.getFund().getTicker(), currPrices);
		});
		strBld.append(String.format("\n%-70s\n", new String(dash)));
		strBld.append(String.format("|%-5s|%10s|%10s|%10s|%10s|", "Ticker", "Cost Price", "Quantity", "Buy Date","Current Price"));
		for(int index=0; index<portfolio.getAllocations().size();index++){
			strBld.append(String.format("\n%-70s\n", new String(dash)));
			Allocation alloc = portfolio.getAllocations().get(index);
			strBld.append(format(alloc, currValueMap.get(alloc.getFund().getTicker())[0]));
		}
		
		double portfolioValue = currValueMap.values().stream().mapToDouble(d-> d[1]).sum();
		strBld.append(String.format("\n\nValue on %s - %s",format2(date),portfolioValue));
		strBld.append(String.format("\nTax Alpha - %s", "not available"));
		return strBld.toString();
	}
	
	public static String format(Allocation allocation, double currentPrice){
		StringBuilder strBld = new StringBuilder();
		strBld.append(String.format("|%-5s|%10s|%10s|%10s|%10s|", allocation.getFund().getTicker(), allocation.getCostPrice(), allocation.getQuantity(), format2(allocation.getTransactionDate()),currentPrice));
		return strBld.toString();
	}
	
	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		context = arg0;
		
	}
}
