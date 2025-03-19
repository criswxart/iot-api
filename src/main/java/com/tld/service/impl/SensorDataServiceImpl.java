package com.tld.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tld.dto.SensorDataDTO;
import com.tld.dto.SensorDataInfoDTO;
import com.tld.jpa.repository.SensorDataRepository;
import com.tld.mapper.SensorDataMapper;
import com.tld.model.SensorData;
import com.tld.service.SensorDataService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorDataServiceImpl implements SensorDataService{
	final SensorDataRepository sensorDataRepository;
	
	@Override
	public String addSensorData(SensorDataDTO sensorDataDTO) {	
	SensorData sensorData=SensorDataMapper.toEntity(sensorDataDTO);
	return sensorDataRepository.save(sensorData).getSensorDataId().getSensorApiKey();	
	}

	@Override
	public SensorDataInfoDTO updateSensorData(String SensorDataApiKey, Long sensorDataCorrel,
			SensorDataDTO sensorDataDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SensorDataInfoDTO> getSensorData(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteSensorData(String SensorDataApiKey, Long sensorDataCorrel) {
		// TODO Auto-generated method stub
		return null;
	}

}
