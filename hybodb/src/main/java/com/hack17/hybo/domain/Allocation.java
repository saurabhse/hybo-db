package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude="portfolio")
public class Allocation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne//(cascade=CascadeType.ALL)
	private Fund fund;
	private double costPrice;
	private int quantity;
	private double percentage;
	private Date transactionDate;
	private double expenseRatio;
	private double investment;
	@ManyToOne
	private Portfolio portfolio;
	private String type;
	private String isActive;
	@Transient
	private Date holdTillDate;
	private String createdBy;
	public Allocation(Fund fund, double costPrice, int quantity,
			double percentage, Date transactionDate, double expenseRatio,double investment) {
		super();
		this.fund = fund;
		this.costPrice = costPrice;
		this.quantity = quantity;
		this.percentage = percentage;
		this.transactionDate = transactionDate;
		this.expenseRatio = expenseRatio;
		this.investment = investment;
	}
	
	
}
