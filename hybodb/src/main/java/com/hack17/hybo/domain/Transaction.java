package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

@Data
@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	@ManyToOne
	@JsonIgnore
	private Portfolio portfolio;
	@ManyToOne
	@JsonUnwrapped
	private Fund fund;
	@JsonProperty("Buy Price")
	private double buyPrice;
	@JsonProperty("Buy Date")
	private Date buyDate;
	@JsonProperty("Sell Price")
	private double sellPrice;
	@JsonProperty("Sell Date")
	private Date sellDate;
	@JsonProperty("Quanity")
	private double quantity;
	@Enumerated(EnumType.STRING)
	@JsonProperty("Action")
	private Action action;
	@Enumerated(EnumType.STRING)
	@JsonProperty("Created By")
	private CreatedBy createdBy;
	
	@JsonIgnore
	private String comments;
}
