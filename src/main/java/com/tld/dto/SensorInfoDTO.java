package com.tld.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SensorInfoDTO {
	private String sensorName;
	private String companyName;
    private String locationAddress;
    private String cityName;          
	private Instant sensorCreatedAt;
	private Instant sensorModifiedAt;
	private Boolean sensorIsActive;

}
