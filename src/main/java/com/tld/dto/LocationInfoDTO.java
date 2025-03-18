package com.tld.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LocationInfoDTO {
    private String companyName;
    private String locationAddress;
    private String cityName;
    private String regionName;
    private String countryName;
    private String locationMeta;
    private String userNameC;
    private String userNameM;
}
