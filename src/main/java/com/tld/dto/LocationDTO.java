package com.tld.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDTO {
	
	private Long locationId;
	private Long companyId;
	private String locationAddress;	
	private Integer cityId;
	private String locationMeta;
	private Integer locationCreatedBy;
	private Integer locationModifiedBy;

}
