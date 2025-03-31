package com.tld.service;

import java.util.List;
import java.util.Optional;

import com.tld.dto.LocationDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.info.LocationInfoDTO;
import com.tld.dto.info.SensorInfoDTO;


public interface SensorService {
	
	//SensorInfoDTO addSensor (SensorDTO sensorDTO);	
	SensorInfoDTO addSensor (SensorDTO sensorDTO);
	SensorInfoDTO updateSensor(String companyApiKey, SensorDTO sensorDTO) ;	
	List<SensorInfoDTO> getSensors(String field, String value, String companyApiKey);
	String deleteSensor(Long  sensorId, String companyApiKey);

	//Usado para ingresar data en sensorDATA
//	Optional<SensorDTO> getSensorByApiKey(String companyApiKey);
	
}
