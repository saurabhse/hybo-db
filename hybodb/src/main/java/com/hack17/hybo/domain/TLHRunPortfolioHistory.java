package com.hack17.hybo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
public class TLHRunPortfolioHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private long portfolioId;
	@JsonProperty(value="Portfolio Value")
	private double portfolioValue;
	@JsonProperty(value="Current Date")
	private String runDate;
	@JsonProperty(value="Tax Loss Harvesting YTD")
	private double tlhValue;
	@JsonProperty(value="Tax Alpha")
	private double taxAlpha;
	@OneToMany(cascade=CascadeType.ALL)
	List<TLHRunAllocationHistory> allocations;
	
}
