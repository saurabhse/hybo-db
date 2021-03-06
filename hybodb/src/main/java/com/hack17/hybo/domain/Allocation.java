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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude="portfolio")
@EqualsAndHashCode(exclude={"id", "isActive","transactionDate","createdBy"})
public class Allocation{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne//(cascade=CascadeType.ALL)
	private Fund fund;
	private double costPrice;
	private double rebalanceDayPrice;
	private int quantity;
	private int rebalanceDayQuantity;
	private double percentage;
	private double rebalanceDayPerc;
	private Date buyDate;
	private Date transactionDate;
	private double expenseRatio;
	private double investment;
	@ManyToOne
	private Portfolio portfolio;
	private String type;
	private String isActive = "Y";
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
	
	public Allocation(Fund fund, double costPrice, int quantity,
			double percentage, Date transactionDate, double expenseRatio,double investment, String createdBy) {
		this(fund, costPrice, quantity, percentage, transactionDate, expenseRatio, investment);
		this.createdBy = createdBy;
	}
	
	public Allocation(Fund fund, double costPrice, int quantity,
			double percentage, Date transactionDate, double expenseRatio,double investment, String createdBy, Portfolio portfolio) {
		this(fund, costPrice, quantity, percentage, transactionDate, expenseRatio, investment,createdBy);
		this.portfolio = portfolio;
	}
	
	public Allocation(Fund fund, double costPrice, int quantity,
			double percentage, Date transactionDate, double expenseRatio,double investment, String createdBy, Date buyDate) {
		this(fund, costPrice, quantity, percentage, transactionDate, expenseRatio, investment,createdBy);
		this.buyDate = buyDate;
	}
	
}
