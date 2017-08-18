package com.hack17.hybo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CurrentDate {
	@Id
	int id;
	@Column(name="dt")
	Date date;
	
}
