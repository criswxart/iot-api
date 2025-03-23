package com.tld.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SensorDataInfoDTO {		
	private Long sensorDataCorrelative;
	private String sensorApiKey;
	private Long sensorId;
    private String companyName;
    private String sensorName;	
    private String sensorEntry;
	private String sensorDataCreatedAt;
	private String sensorDataModifiedAt;
	private Boolean sensorDataIsActive;
}

