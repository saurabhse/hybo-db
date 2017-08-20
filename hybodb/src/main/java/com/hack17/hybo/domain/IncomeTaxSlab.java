package com.hack17.hybo.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class IncomeTaxSlab {
	public static enum TaxProfileType{
		SINGLE, MARRIED_FILING_JOINTLY_OR_QUALIFYING_WIDOW, MARRIED_FILING_SEPARATELY, HEAD_OF_HOUSEHOLD
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private TaxProfileType taxProfileType;
	private Double slabStart;
	private Double slabEnd;
	private Double taxRate;
	public IncomeTaxSlab(TaxProfileType taxProfileType, Double slabStart,
			Double slabEnd, Double taxRate) {
		super();
		this.taxProfileType = taxProfileType;
		this.slabStart = slabStart;
		this.slabEnd = slabEnd;
		this.taxRate = taxRate;
	}
	
	
	
	
}
