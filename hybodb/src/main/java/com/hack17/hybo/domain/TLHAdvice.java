package com.hack17.hybo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class TLHAdvice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	private Portfolio portfolio;
	
	private Date advisedOnDate;
	
	private boolean reviewed;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Recommendation> recommendations = new ArrayList<>();
	
	
	

}
