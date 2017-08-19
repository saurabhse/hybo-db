package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class SecurityPrice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String ticker;
	private double price;
	private Date priceDate;
}
