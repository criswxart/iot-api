package com.tld.service;

import java.util.List;

import com.tld.dto.MeasurementDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.dto.info.MeasurementInfoDTO;
import com.tld.dto.info.SensorDataInfoDTO;

public interface MeasurementService {
	
	MeasurementDTO addSensorData (MeasurementDTO measurementDTO, String sensorApiKey);	
	MeasurementInfoDTO updateSensorData(String sensorApiKey, Long measurementId, String companyApiKey);
	MeasurementInfoDTO getSensorDataById(Long measurementID,String sensorApiKey, String companyApiKey);
	List<MeasurementInfoDTO>getSensorDataByEpoch(Long from, Long to, String companyApiKey);
	List<MeasurementInfoDTO>getSensorDataByCompany(String companyApiKey);
	MeasurementInfoDTO deleteSensorData(String sensorApiKey, Long measurementID,String companyApiKey);


}
