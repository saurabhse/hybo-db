package com.hack17.hybo.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class One {

	@Id
	int id;
	
	@Column(name="STOCK_ID")
	String stockId;
	
	@OneToMany
	@JoinColumn(name="CHILD_ID")
	Set<Two> setOfTwo;
}
