package com.tld.service;

import java.util.List;
import java.util.Optional;

import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.SensorInfoDTO;


public interface SensorService {
	
	SensorInfoDTO addSensor (SensorDTO sensorDTO);	
	SensorInfoDTO updateSensor(String companyApiKey, SensorDTO sensorDTO) ;	
	List<SensorInfoDTO> getSensors(String field, String value, String companyApiKey);
	String deleteSensor(Long  sensorId, String companyApiKey);

	//Usado para ingresar data en sensorDATA
	Optional<SensorDTO> getSensorByApiKey(String companyApiKey);
	
}
