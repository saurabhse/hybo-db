package com.hack17.hybo.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Data
@Entity
public class Portfolio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int clientId;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@Fetch(FetchMode.JOIN)
	private List<Allocation> allocations = new ArrayList<>();
	@OneToOne//(cascade=CascadeType.ALL)
	private InvestorProfile investorProfile;
	@Column
	private Date transactionDate;

	public List<Allocation> getAllocations() {
		return allocations;
	}

	public void addAllocation(Allocation allocation) {
		allocation.setPortfolio(this);
		allocations.add(allocation);
		
	}

	public InvestorProfile getInvestorProfile() {
		return investorProfile;
	}

	public void setInvestorProfile(InvestorProfile investorProfile) {
		this.investorProfile = investorProfile;
	}

	
	
	

}
