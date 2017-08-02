package com.hack17.hybo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;





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
		strBld.append(String.format("\nValue on - %s\n",date));
		char [] dash = new char[40];
		Arrays.fill(dash, '-');
		portfolio.getAllocations().forEach(alloc->{
			strBld.append(String.format("\n%50s\n", new String(dash)));
			strBld.append(format(alloc));
		});
		List<Double> currValueList = new ArrayList<>();
		portfolio.getAllocations().forEach(alloc->{
			double currVal = refDataRepo.getPriceOnDate(alloc.getFund().getTicker(), date);
			currValueList.add(currVal*alloc.getQuantity());
		});
		double portfolioValue = currValueList.stream().mapToDouble(d-> d).sum();
		strBld.append(String.format("\n\nPortfolio Value - %s", portfolioValue));
		strBld.append(String.format("\nTax Alpha - %s", "not available"));
		return strBld.toString();
	}
	
	public static String format(Allocation allocation){
		StringBuilder strBld = new StringBuilder();
		strBld.append(String.format("%10s|%10s|%10s|%10s|", allocation.getFund().getTicker(), allocation.getCostPrice(), allocation.getQuantity(), allocation.getTransactionDate()));
		return strBld.toString();
	}
	
	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		context = arg0;
		
	}
}
