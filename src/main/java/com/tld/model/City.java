package com.tld.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name="city")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="city_id")
	private Integer cityId;	
	
	@Column(name="city_name", nullable = false, unique = true)
	private String cityName;	
	
	@ManyToOne
    @JoinColumn (name="region_id", nullable = false)
	private Region region;
	
	public City(Integer cityId) {
		this.cityId=cityId;
	}
	
}
