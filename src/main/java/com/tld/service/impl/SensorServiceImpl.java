package com.tld.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tld.dto.SensorDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.mapper.SensorMapper;
import com.tld.model.Sensor;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService{
	
	final SensorRepository sensorRepository;	

	@Override
	public Long addSensor(SensorDTO sensorDTO) {		
		Sensor sensor=SensorMapper.toEntity(sensorDTO);
		return sensorRepository.save(sensor).getSensorId();
	}

	@Override
	public Optional<SensorDTO> getSensorByApiKey(String apiKey) {		
		return sensorRepository.findBySensorApiKey(apiKey).map(SensorMapper::toDTO);
	}

		
}
