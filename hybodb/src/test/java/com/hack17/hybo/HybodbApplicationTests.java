package com.hack17.hybo;

import static com.hack17.hybo.util.DateTimeUtil.getDate;
import static com.hack17.hybo.util.DateTimeUtil.getDate2;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.hack17.hybo.domain.Allocation;
import com.hack17.hybo.domain.CorrelatedFund;
import com.hack17.hybo.domain.Fund;
import com.hack17.hybo.domain.InvestorProfile;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.RiskTolerance;
import com.hack17.hybo.domain.SecurityPrice;
import com.hack17.hybo.repository.FundRepository;
import com.hack17.hybo.service.DBLoggerService;

import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HybodbApplicationTests {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private FundRepository fundRepo;
	
	@Autowired
	private DBLoggerService dbLoggerService;
	
	@Before
	public void setUp() throws Exception {
		//createFunds();
		//createInvestorProfile();
		//createPortfolios();
		//createAllocations();
		//createPrice();
		createCorrelatedFund();
	}

	private void createCorrelatedFund() {
		CorrelatedFund correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("VTI");
		correlatedFund.setCorrelatedTicker("SCHB");
		entityManager.persist(correlatedFund);
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("VEA");
		correlatedFund.setCorrelatedTicker("SCHF");
		entityManager.persist(correlatedFund);
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("VWO");
		correlatedFund.setCorrelatedTicker("IEMG");
		entityManager.persist(correlatedFund);
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("VIG");
		correlatedFund.setCorrelatedTicker("SCHD");
		entityManager.persist(correlatedFund);
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("XLE");
		correlatedFund.setCorrelatedTicker("VDE");
		entityManager.persist(correlatedFund);
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("SCHP");
		correlatedFund.setCorrelatedTicker("VTIP");
		entityManager.persist(correlatedFund);
		correlatedFund = new CorrelatedFund();
		correlatedFund.setTicker("MUB");
		correlatedFund.setCorrelatedTicker("TFI");
		entityManager.persist(correlatedFund);
		entityManager.flush();
	}

	private void createPrice() {
//		SecurityPrice price = new SecurityPrice();
//		price.setTicker("VTI");
//		price.setPrice(124d);
//		price.setPriceDate(getDate("JUL 01, 2017"));
//		entityManager.persist(price);
//		price = new SecurityPrice();
//		price.setTicker("VTV");
//		price.setPrice(96d);
//		price.setPriceDate(getDate("JUL 01, 2017"));
//		entityManager.persist(price);
//		price = new SecurityPrice();
//		price.setTicker("VEA");
//		price.setPrice(41d);
//		price.setPriceDate(getDate("JUL 01, 2017"));
//		entityManager.persist(price);
		File priceDir = new File("./data/prices");
		for(File priceFile : priceDir.listFiles()){
			String fileName = priceFile.getName();
			String etf = fileName.substring(0, fileName.indexOf("."));
			System.out.println(etf);
			try(Stream<String> fileLines = Files.lines(Paths.get(priceFile.toURI()))){
				fileLines.forEach(line->{
					String[] lineData = line.split(",");
					if(lineData.length !=6)
						return;
					if(lineData[0].contains("Date"))
						return;
					SecurityPrice price = new SecurityPrice();
					price.setTicker(etf.toUpperCase());
					price.setPrice(Double.parseDouble(lineData[4]));
					price.setPriceDate(getDate2(lineData[0]));
					entityManager.persist(price);
				});
			} catch (IOException e) {
				System.out.println(e.getMessage());// TODO Auto-generated catch block
			}
		}
		
		entityManager.flush();
		
	}

	private void createInvestorProfile() {
		InvestorProfile investorProfile = new InvestorProfile(getDate("Apr 7, 1972"), RiskTolerance.MODERATE, 26, getDate("Jan 1, 2017"));
		//investorProfile.setId(501);
		entityManager.persist(investorProfile);
		entityManager.flush();
		
	}

	private void createPortfolios() {
		InvestorProfile investorProfile = entityManager.createQuery("from InvestorProfile", InvestorProfile.class).getSingleResult();
		Portfolio portfolio = new Portfolio();
		portfolio.setInvestorProfile(investorProfile);
		Fund fundVTI = fundRepo.findFund("VTI");
		//Allocation alloc = new Allocation(fundVTI,76.58,1000,50d, getDate("OCT 01, 2007"), .04,0);
		Allocation alloc = new Allocation(fundVTI,73.23,1000,50d, getDate("Nov 01, 2012"), .04,0);
		portfolio.addAllocation(alloc);
		Fund fundVEA = fundRepo.findFund("VEA");
//		alloc = new Allocation(fundVEA,50.86,1000,50d, getDate("OCT 01, 2007"), .06,0);
		alloc = new Allocation(fundVEA,33.62,1000,50d, getDate("Nov 01, 2012"), .06,0);
		portfolio.addAllocation(alloc);
		Fund fundVWO = fundRepo.findFund("VWO");
//		alloc = new Allocation(fundVWO,53.88,1000,50d, getDate("OCT 01, 2007"), .04,0);
		alloc = new Allocation(fundVWO,42.09,1000,50d, getDate("Nov 01, 2012"), .04,0);
		portfolio.addAllocation(alloc);
		Fund fundVIG = fundRepo.findFund("VIG");
//		alloc = new Allocation(fundVIG,58.53,1000,50d, getDate("OCT 01, 2007"), .06,0);
		alloc = new Allocation(fundVIG,59.52,1000,50d, getDate("Nov 01, 2012"), .06,0);
		portfolio.addAllocation(alloc);
		Fund fundXLE = fundRepo.findFund("XLE");
//		alloc = new Allocation(fundXLE,76.0,1000,50d, getDate("OCT 01, 2007"), .04,0);
		alloc = new Allocation(fundXLE,72.25,1000,50d, getDate("Nov 01, 2012"), .04,0);
		portfolio.addAllocation(alloc);
		Fund fundMUB = fundRepo.findFund("MUB");
//		alloc = new Allocation(fundMUB,100.6,1200,50d, getDate("OCT 01, 2007"), .06,0);
		alloc = new Allocation(fundMUB,111.97,1200,50d, getDate("Nov 01, 2012"), .06,0);
		portfolio.addAllocation(alloc);
		//portfolio.setId(101l);
		entityManager.persist(portfolio);
//		portfolio = new Portfolio();
//		alloc = new Allocation(fundVTI,120.4,1000,50d, getDate("APR 01, 2017"), .04,0);
//		portfolio.addAllocation(alloc);
//		alloc = new Allocation(fundVEA,105.01,1200,50d, getDate("APR 01, 2017"), .06,0);
//		portfolio.addAllocation(alloc);
//		portfolio.setInvestorProfile(investorProfile);
//		//portfolio.setId(102l);
//		entityManager.persist(portfolio);
//		portfolio = new Portfolio();
//		alloc = new Allocation(fundVTI,120.4,1000,50d, getDate("APR 01, 2017"), .04,0);
//		portfolio.addAllocation(alloc);
//		alloc = new Allocation(fundVEA,105.01,1200,50d, getDate("JUL 01, 2017"), .06,0);
//		portfolio.addAllocation(alloc);
//		portfolio.setInvestorProfile(investorProfile);
//		//portfolio.setId(103l);
//		entityManager.persist(portfolio);
		entityManager.flush();
		
	}

	private Allocation findAllocation(long allocId) {
		return entityManager.find(Allocation.class, allocId);
	}

	private void createAllocations() {
		Fund fundVTI = fundRepo.findFund("VTI");
		Allocation alloc = new Allocation(fundVTI,76.58,1000,50d, getDate("OCT 01, 2007"), .04,0);
		//alloc.setId(301l);
		entityManager.persist(alloc);
		Fund fundVEA = fundRepo.findFund("VEA");
		alloc = new Allocation(fundVEA,72.51,1000,50d, getDate("OCT 01, 2007"), .06,0);
		//alloc.setId(302l);
		entityManager.persist(alloc);
		
		Fund fundVWO = fundRepo.findFund("VWO");
		alloc = new Allocation(fundVWO,53.88,1000,50d, getDate("OCT 01, 2007"), .04,0);
		//alloc.setId(303l);
		entityManager.persist(alloc);
		
		Fund fundVIG = fundRepo.findFund("VIG");
		alloc = new Allocation(fundVIG,58.53,1000,50d, getDate("OCT 01, 2007"), .06,0);
		//alloc.setId(304l);
		entityManager.persist(alloc);
		
		Fund fundXLE = fundRepo.findFund("XLE");
		alloc = new Allocation(fundXLE,76.0,1000,50d, getDate("OCT 01, 2007"), .04,0);
		//alloc.setId(305l);
		entityManager.persist(alloc);
		
		Fund fundMUB = fundRepo.findFund("MUB");
		alloc = new Allocation(fundMUB,100.6,1300,50d, getDate("OCT 01, 2007"), .06,0);
		//alloc.setId(306l);
		entityManager.persist(alloc);
		
		entityManager.flush();
		
		
	}

	

	private void createFunds() {
		Fund fund = new Fund();
		fund.setTicker("VTI");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("VEA");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("VWO");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("VIG");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("XLE");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("SCHP");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("MUB");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("SCHB");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("SCHF");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("IEMG");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("SCHD");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("VDE");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("VTIP");
		entityManager.persist(fund);
		fund = new Fund();
		fund.setTicker("TFI");
		entityManager.persist(fund);
		entityManager.flush();
	}
	
	@Test 
	@Transactional
	@Rollback(false)
	public void portfolioLoads() {
		Portfolio portfolio = entityManager.createQuery("from Portfolio", Portfolio.class).getResultList().get(0);
		System.out.println(portfolio);
		assertNotNull(portfolio);
//		Optional<Allocation> allocation = portfolio.getAllocations().stream().filter(alloc->alloc.getFund().getTicker().equals("MUB")).findFirst();
//		dbLoggerService.logTransaction(allocation.get(), 65d, new Date(), 100);
//		allocation.get().setQuantity(1200);
//		entityManager.persist(allocation.get());
	}

}
