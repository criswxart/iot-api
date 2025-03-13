package com.tld.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name="country")
public class Country {
	
	@Id	
	@Column(name="country_id")
	private Integer countryId;	
	
	@Column(name="country_name", nullable = false, unique = true)
	private String countryName;
	
	public Country(Integer countryId) {
		this.countryId=countryId;
	}

}
