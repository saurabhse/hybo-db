package com.hack17.hybo.repository;

import static com.hack17.hybo.util.DateTimeUtil.getDate;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.InvestorProfile;
import com.hack17.hybo.domain.RiskTolerance;

@Repository
public class InvestorRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public InvestorProfile createProfile(Date dob, RiskTolerance riskTolerance, int investmentHorizon, Date horizonAsOfDate){
		InvestorProfile investorProfile = new InvestorProfile(dob, riskTolerance, 120, horizonAsOfDate);
		entityManager.persist(investorProfile);
		entityManager.flush();
		return investorProfile;
	}
	
	@Transactional
	public InvestorProfile createProfile(Date dob, RiskTolerance riskTolerance, int investmentHorizon, Date horizonAsOfDate, int annualIncome){
		InvestorProfile investorProfile = new InvestorProfile(dob, riskTolerance, 120, horizonAsOfDate, annualIncome);
		entityManager.persist(investorProfile);
		entityManager.flush();
		return investorProfile;
	}
	
	public InvestorProfile getProfileById(long profileId){
		TypedQuery<InvestorProfile> typedQuery = entityManager.createQuery("from InvestorProfile where id=?", InvestorProfile.class);
		typedQuery.setParameter(1, profileId);
		return typedQuery.getResultList().get(0);
	}
	
	public void deleteAllProfiles(){
		entityManager.createQuery("delete from InvestorProfile").executeUpdate();
	}
}
