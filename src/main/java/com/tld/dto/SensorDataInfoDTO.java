package com.tld.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SensorDataInfoDTO {	
	
    private String companyName;
    private String sensorName;	
    private String sensorEntry;
	private Instant sensorDataCreatedAt;
}

