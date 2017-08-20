package com.hack17.hybo;

import static com.hack17.hybo.util.DateTimeUtil.getDateMMMddyyyy;
import static com.hack17.hybo.util.DateTimeUtil.getDatedd_MMM_yyyy;
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
import com.hack17.hybo.domain.IncomeTaxSlab;
import com.hack17.hybo.domain.InvestorProfile;
import com.hack17.hybo.domain.Portfolio;
import com.hack17.hybo.domain.RiskTolerance;
import com.hack17.hybo.domain.SecurityPrice;
import com.hack17.hybo.repository.FundRepository;
import com.hack17.hybo.repository.IncomeTaxSlabRepository;
import com.hack17.hybo.repository.ReferenceDataRepository;
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
	
	@Autowired
	private IncomeTaxSlabRepository incomeTaxSlabRepo;
	
	@Autowired
	private ReferenceDataRepository refDataRepo;
	
	@Before
	public void setUp() throws Exception {
		insertTaxSlabs();
		createFunds();
		createInvestorProfile();
		createPortfolios();
		//createAllocations();
		createPrice();
		createCorrelatedFund();
	}
	
	private void insertTaxSlabs() {
		//single
		IncomeTaxSlab taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 0d, 9325d, 10d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 9326d, 37950d, 15d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 37951d, 91900d, 25d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 91901d, 191650d, 28d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 191651d, 416700d, 33d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 416701d, 418400d, 35d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.SINGLE, 418401d, null, 39.6d);
		incomeTaxSlabRepo.persist(taxSlab);
		
		//Married Filing Jointly / Qualifying Widow
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 0d, 18650d, 10d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 18651d, 75900d, 15d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 75901d, 153100d, 25d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 153101d, 233350d, 28d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 233351d, 416700d, 33d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 416701d, 470700d, 35d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, 470701d, null, 39.6d);
		incomeTaxSlabRepo.persist(taxSlab);
		
		//Married Filing Separately
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 0d, 9325d, 10d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 9326d, 37950d, 15d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 37951d, 76550d, 25d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 76551d, 116675d, 28d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 116676d, 208350d, 33d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 208351d, 235350d, 35d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.MARRIED_FILING_SEPARATELY, 235351d, null, 39.6d);
		incomeTaxSlabRepo.persist(taxSlab);
		
		//Head of Household
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 0d, 13350d, 10d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 13351d, 50800d, 15d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 50801d, 131200d, 25d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 131201d, 212500d, 28d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 212501d, 416700d, 33d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 416701d, 444550d, 35d);
		incomeTaxSlabRepo.persist(taxSlab);
		taxSlab = new IncomeTaxSlab(IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD, 444551d, null, 39.6d);
		incomeTaxSlabRepo.persist(taxSlab);
	}

	private void createCorrelatedFund() {
		refDataRepo.createCorrelatedFund("VTI", "SCHB");
		refDataRepo.createCorrelatedFund("VEA", "SCHF");
		refDataRepo.createCorrelatedFund("VWO", "IEMG");
		refDataRepo.createCorrelatedFund("VIG", "SCHD");
		refDataRepo.createCorrelatedFund("XLE", "VDE");
		refDataRepo.createCorrelatedFund("SCHP", "VTIP");
		refDataRepo.createCorrelatedFund("MUB", "TFI");
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
					price.setPriceDate(getDatedd_MMM_yyyy(lineData[0]));
					entityManager.persist(price);
				});
			} catch (IOException e) {
				System.out.println(e.getMessage());// TODO Auto-generated catch block
			}
		}
		
		entityManager.flush();
		
	}

	private void createInvestorProfile() {
		InvestorProfile investorProfile = new InvestorProfile(getDateMMMddyyyy("Apr 7, 1972"), RiskTolerance.MODERATE, 26, getDateMMMddyyyy("Jan 1, 2017"));
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
		Allocation alloc = new Allocation(fundVTI,73.78,5000,50d, getDateMMMddyyyy("Oct 01, 2012"), .04,0);
		portfolio.addAllocation(alloc);
		Fund fundVEA = fundRepo.findFund("VEA");
//		alloc = new Allocation(fundVEA,50.86,1000,50d, getDate("OCT 01, 2007"), .06,0);
		alloc = new Allocation(fundVEA,33.14,5000,50d, getDateMMMddyyyy("Oct 01, 2012"), .06,0);
		portfolio.addAllocation(alloc);
		Fund fundVWO = fundRepo.findFund("VWO");
//		alloc = new Allocation(fundVWO,53.88,1000,50d, getDate("OCT 01, 2007"), .04,0);
		alloc = new Allocation(fundVWO,42.16,5000,50d, getDateMMMddyyyy("Oct 01, 2012"), .04,0);
		portfolio.addAllocation(alloc);
		Fund fundVIG = fundRepo.findFund("VIG");
//		alloc = new Allocation(fundVIG,58.53,1000,50d, getDate("OCT 01, 2007"), .06,0);
		alloc = new Allocation(fundVIG,59.89,5000,50d, getDateMMMddyyyy("Oct 01, 2012"), .06,0);
		portfolio.addAllocation(alloc);
		Fund fundXLE = fundRepo.findFund("XLE");
//		alloc = new Allocation(fundXLE,76.0,1000,50d, getDate("OCT 01, 2007"), .04,0);
		alloc = new Allocation(fundXLE,73.80,5000,50d, getDateMMMddyyyy("Oct 01, 2012"), .04,0);
		portfolio.addAllocation(alloc);
		Fund fundMUB = fundRepo.findFund("MUB");
//		alloc = new Allocation(fundMUB,100.6,1200,50d, getDate("OCT 01, 2007"), .06,0);
		alloc = new Allocation(fundMUB,111.68,1200,50d, getDateMMMddyyyy("Oct 01, 2012"), .06,0);
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
		Allocation alloc = new Allocation(fundVTI,76.58,1000,50d, getDateMMMddyyyy("OCT 01, 2007"), .04,0);
		//alloc.setId(301l);
		entityManager.persist(alloc);
		Fund fundVEA = fundRepo.findFund("VEA");
		alloc = new Allocation(fundVEA,72.51,1000,50d, getDateMMMddyyyy("OCT 01, 2007"), .06,0);
		//alloc.setId(302l);
		entityManager.persist(alloc);
		
		Fund fundVWO = fundRepo.findFund("VWO");
		alloc = new Allocation(fundVWO,53.88,1000,50d, getDateMMMddyyyy("OCT 01, 2007"), .04,0);
		//alloc.setId(303l);
		entityManager.persist(alloc);
		
		Fund fundVIG = fundRepo.findFund("VIG");
		alloc = new Allocation(fundVIG,58.53,1000,50d, getDateMMMddyyyy("OCT 01, 2007"), .06,0);
		//alloc.setId(304l);
		entityManager.persist(alloc);
		
		Fund fundXLE = fundRepo.findFund("XLE");
		alloc = new Allocation(fundXLE,76.0,1000,50d, getDateMMMddyyyy("OCT 01, 2007"), .04,0);
		//alloc.setId(305l);
		entityManager.persist(alloc);
		
		Fund fundMUB = fundRepo.findFund("MUB");
		alloc = new Allocation(fundMUB,100.6,1300,50d, getDateMMMddyyyy("OCT 01, 2007"), .06,0);
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
