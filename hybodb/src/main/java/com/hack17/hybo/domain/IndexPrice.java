package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class IndexPrice {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@Column
	String index;
	
	@Column
	Date date;
	@Column
	double open;
	@Column
	double high;
	@Column
	double low;
	@Column
	double price;
	@Column
	int volumn;
	@Column
	double change;
	
}
