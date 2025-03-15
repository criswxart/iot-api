package com.tld.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString

public class LocationDTO {
	
	private Long locationId;
	private Long companyId;
	private String locationAdress;	
	private Integer cityId;
	private String locationMeta;
	private Integer locationCreatedBy;
	private Long locationCreatedAt;
	private Boolean locationActive;
	private Integer locationModifiedBy;
	private Long locationModifiedAt;

}
