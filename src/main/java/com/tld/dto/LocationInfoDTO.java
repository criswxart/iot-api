package com.tld.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LocationInfoDTO {
	private Long locationId;
    private String companyName;
    private String locationAddress;
    private String cityName;
    private String regionName;
    private String countryName;
    private String locationMeta;    
    private String locationCreatedBy;
    private String locationCreatedAt;    			
    private String locationModifiedBy;
    private String locationModifiedAt;
}
