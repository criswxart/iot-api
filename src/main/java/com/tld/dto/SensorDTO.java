package com.tld.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString

public class SensorDTO {
	
	private Long sensorId;		
	private Long locationId;	
	private String sensorName;	
	private Integer categoryId;	
	private String sensorMeta;	
	private String sensorApiKey;		

}
