package com.tld.service;

import java.util.List;

import com.tld.dto.SensorDataDTO;
import com.tld.dto.SensorDataInfoDTO;

public interface SensorDataService {
	
	
	String addSensorData (SensorDataDTO sensorDataDTO);	
	SensorDataInfoDTO updateSensorData(String SensorDataApiKey, Long sensorDataCorrel, SensorDataDTO sensorDataDTO) ;
	List<SensorDataInfoDTO>getSensorData(String field, String value);
	String deleteSensorData(String SensorDataApiKey, Long sensorDataCorrel);

}
