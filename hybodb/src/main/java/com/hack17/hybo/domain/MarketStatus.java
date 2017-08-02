package com.hack17.hybo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MarketStatus {

	@Id
	@Column(name="ID")
	public int id;
	@Column(name="IS_GOING_UP")
	@Type(type="yes/no")
	public boolean isGoingUp;
	@Column(name="IS_GOING_DOWN")
	public boolean isGoingDown;
	@Column(name="IS_FLUCTUATING")
	public boolean isFluctuating;
	
	
}
