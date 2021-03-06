package com.hack17.hybo.domain;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hack17.hybo.util.DoubleFormatter;

import lombok.Data;

@Entity
@Data
public class TLHRunPortfolioHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	private long portfolioId;
	@JsonProperty(value="Portfolio Value")
	@JsonSerialize(using=DoubleFormatter.class)
	private double portfolioValue;
	@JsonProperty(value="Current Date")
	@JsonFormat(pattern="dd-MMM-yyyy")
	private Date runDate;
	@JsonProperty(value="Tax Loss Harvesting YTD")
	@JsonSerialize(using=DoubleFormatter.class)
	private double tlhValue;
	@JsonProperty(value="Tax Alpha")
	@JsonSerialize(using=DoubleFormatter.class)
	private double taxAlpha;
	@OneToMany(cascade=CascadeType.ALL)
	@JsonIgnore
	List<TLHRunAllocationHistory> allocations;
	@Transient
	@JsonProperty(value="Transactions")
	List<Transaction> transactions;
	
}
