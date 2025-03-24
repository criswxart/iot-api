package com.tld.service;

import java.util.List;

import com.tld.dto.SensorDataDTO;
import com.tld.dto.SensorDataInfoDTO;

public interface SensorDataService {
	
	
	String addSensorData (SensorDataDTO sensorDataDTO);	
	SensorDataInfoDTO updateSensorData(SensorDataDTO sensorDataDTO, String companyApiKey) ;
	SensorDataInfoDTO getSensorDataById(Long correlative, String sensorApiKey, String companyApiKey);
	List<SensorDataInfoDTO>getSensorDataByEpoch(Long field, Long value, String companyApiKey);
	List<SensorDataInfoDTO>getSensorDataByCompany(String companyApiKey);
	SensorDataInfoDTO deleteSensorData(String sensorApiKey, Long sensorDataCorrel, String companyApiKey);

}
