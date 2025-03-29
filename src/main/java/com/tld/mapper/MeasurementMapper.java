package com.tld.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tld.dto.MeasurementDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.model.Measurement;
import com.tld.model.Sensor;
import com.tld.model.SensorData;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class MeasurementMapper {
	
	private final SensorDataMapper sensorDataMapper; 
	
	
	public MeasurementDTO toDTO(Measurement measurement) {
		
		 List<SensorDataDTO> sensorDataDTOList = measurement.getSensorDataList()
	                .stream()
	                .map(sensorDataMapper::toDTO)
	                .collect(Collectors.toList());

	        return new MeasurementDTO(
	                measurement.getMeasurementId(),
	                measurement.getSensor().getSensorApiKey(),
	                sensorDataDTOList,
	                measurement.getMeasurementIsActive()
	        );     
	}

	public Measurement toEntity(MeasurementDTO measurementDTO) {	 
	       List<SensorData> sensorDataList = measurementDTO.getJson_data()
	                .stream()
	                .flatMap(dto -> sensorDataMapper.toEntityList(dto).stream()) // Convertir múltiples métricas
	                .collect(Collectors.toList());

	        return new Measurement(
	                measurementDTO.getMeasurementId(),
	                new Sensor(measurementDTO.getApi_key()),
	                sensorDataList,
	                measurementDTO.getMeasurementIsActive()
	        );
	}
	
	
	

}
