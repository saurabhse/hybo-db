package com.hack17.hybo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MarketStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	@Type(type="yes_no")
	public boolean isGoingUp;
	@Type(type="yes_no")
	public boolean isGoingDown;
	@Type(type="yes_no")
	public boolean isFluctuating;
	
	
}
