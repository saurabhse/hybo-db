package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hack17.hybo.util.DateTimeUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TLHRunAllocationHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	@JsonProperty(value="Ticker")
	private String ticker;
	@JsonProperty(value="Buy Price")
	private double buyPrice;
	@JsonProperty(value="Quantity")
	private double quantity;
	@JsonProperty(value="Buy Date")
	private String buyDate;
	@JsonProperty(value="Current Price")
	private double currentPrice;
	@JsonProperty(value="Created By")
	private String createdBy;
	
	public TLHRunAllocationHistory(Allocation allocation){
		ticker = allocation.getFund().getTicker();
		buyPrice = allocation.getCostPrice();
		quantity = allocation.getQuantity();
		buyDate = DateTimeUtil.format2(allocation.getBuyDate());
		createdBy = allocation.getCreatedBy();
	}
	
	
}
