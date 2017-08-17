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
	
	@Column(name="idx")
	String index;
	
	@Column(name="dt")
	Date date;
	@Column(name="op")
	Double open;
	@Column(name="hgh")
	Double high;
	@Column(name="lw")
	Double low;
	@Column(name="prc")
	Double price;
	@Column(name="vlm")
	int volumn;
	@Column(name="chg")
	double change;
	
}
