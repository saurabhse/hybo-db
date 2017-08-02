package com.hack17.hybo.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity

public class Recommendation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String ticker1;
	private String ticker2;
	@Enumerated(EnumType.STRING)
	private Action action;
	private int quantity;
	public Recommendation(){
		
	}
	public Recommendation(String ticker1, String ticker2, Action action) {
		super();
		this.ticker1 = ticker1;
		this.ticker2 = ticker2;
		this.action = action;
	}
	public Recommendation(String ticker1, String ticker2, Action action,
			int quantity) {
		super();
		this.ticker1 = ticker1;
		this.ticker2 = ticker2;
		this.action = action;
		this.quantity = quantity;
	}
	
	
	
	
}
