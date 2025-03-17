package com.tld.service;

import java.util.Optional;

import com.tld.dto.SensorDTO;


public interface SensorService {

	Long addSensor (SensorDTO sensorDTO);
	Optional<SensorDTO> getSensorByApiKey(String apiKey);
	
}
