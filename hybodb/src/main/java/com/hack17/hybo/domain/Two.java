package com.hack17.hybo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Two {

	@Id
	int id;
	
	@Column(name="CHILD_ID")
	String childId;
	
	@Column(name="NAME")
	String name;
}
