package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class InvestorProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private Date dateOfBirth;
	@Enumerated(EnumType.STRING)
	private RiskTolerance riskTolerance;
	private int investmentHorizonInMonths;
	private Date horizonAsOfDate;
	//@Transient
	private int annualIncome=200000;

	public InvestorProfile(Date dateOfBirth, RiskTolerance riskTolerance,
			int investmentHorizon, Date horizonAsOfDate) {
		super();
		this.dateOfBirth = dateOfBirth;
		this.riskTolerance = riskTolerance;
		this.investmentHorizonInMonths = investmentHorizon;
		this.horizonAsOfDate = horizonAsOfDate;
	}
	
	public InvestorProfile(Date dateOfBirth, RiskTolerance riskTolerance,
			int investmentHorizon, Date horizonAsOfDate, int annualIncome) {
		this(dateOfBirth, riskTolerance, investmentHorizon, horizonAsOfDate);
		this.annualIncome = annualIncome;
	}

	@Override
	public String toString() {
		return "InvestorProfile [id=" + id + ", dateOfBirth=" + dateOfBirth + ", riskTolerance=" + riskTolerance
				+ ", investmentHorizonInMonths=" + investmentHorizonInMonths + ", horizonAsOfDate=" + horizonAsOfDate
				+ "]";
	}
	
	

}
