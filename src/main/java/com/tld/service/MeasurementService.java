package com.tld.service;

import java.util.List;

import com.tld.dto.MeasurementDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.dto.info.MeasurementInfoDTO;
import com.tld.dto.info.SensorDataInfoDTO;

public interface MeasurementService {
	
	MeasurementDTO addSensorData (MeasurementDTO measurementDTO, String sensorApiKey);	
	SensorDataInfoDTO updateSensorData(MeasurementDTO measurementDTO, String companyApiKey) ;
	MeasurementInfoDTO getSensorDataById(Long measurementID,String sensorApiKey, String companyApiKey);
	List<SensorDataInfoDTO>getSensorDataByEpoch(Long field, Long value, String companyApiKey);
	List<SensorDataInfoDTO>getSensorDataByCompany(String companyApiKey);
	SensorDataInfoDTO deleteSensorData(String sensorApiKey, Long measurementID,String companyApiKey);


}
