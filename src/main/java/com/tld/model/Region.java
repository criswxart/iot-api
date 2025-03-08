package com.tld.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table (name="region")
public class Region {
	
	@Id	
	@Column(name="region_id")
	private Integer regionId;	
	
	@Column(name="region_name", nullable = false, unique = true)
	private String regionName;	

	@ManyToOne
    @JoinColumn(name = "country_id", nullable = false)	
	private Country countryId;

	
}
