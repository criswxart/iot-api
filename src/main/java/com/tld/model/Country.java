package com.tld.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table (name="country")
public class Country {
	
	@Id	
	@Column(name="country_id")
	private Integer countryId;	
	
	@Column(name="country_name", nullable = false, unique = true)
	private String countryName;

}
