package com.tld.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeasurementDTO {
	
	private Long measurementId;	
	private String sensorApiKey;
	private List<SensorDataDTO> sensorDataList;
	private Boolean measurementIsActive;
}
