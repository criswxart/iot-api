package com.tld.service.impl;

import org.springframework.stereotype.Service;

import com.tld.dto.SensorDataDTO;
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

}
