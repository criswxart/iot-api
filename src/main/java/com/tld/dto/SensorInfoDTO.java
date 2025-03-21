package com.tld.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SensorInfoDTO {
	private Long sensorId;
	private String sensor_api_key;
	private String sensorName;
	private String companyName;
    private String locationAddress;
    private String cityName;          
	private String sensorCreatedAt;
	private String sensorModifiedAt;
	private Boolean sensorIsActive;

}
