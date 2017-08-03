package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Portfolio portfolio;
	@ManyToOne
	private Fund fund;
	private double buyPrice;
	private Date buyDate;
	private double sellPrice;
	private Date sellDate;
	private double sellQuantity;
	@Enumerated(EnumType.STRING)
	private Action action;
}
