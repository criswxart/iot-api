package com.tld.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tld.dto.MeasurementDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.model.Measurement;
import com.tld.model.SensorData;

@Component
public class MeasurementMapper {
	
	private final SensorDataMapper sensorDataMapper; 
	
    public MeasurementMapper(SensorDataMapper sensorDataMapper) {
        this.sensorDataMapper = sensorDataMapper;
    }
	
	public MeasurementDTO toDTO(Measurement measurement) {
		
		List<SensorDataDTO> sensorDataDTOList=measurement.getSensorDataList()
				 .stream()
				 .map(sensorDataMapper::toDTO)
				 .collect(Collectors.toList());
		return new MeasurementDTO(	measurement.getMeasurementId(),
									measurement.getSensor().getSensorApiKey(),					
									sensorDataDTOList,		
									measurement.getMeasurementIsActive());	        
	}

	public Measurement toEntity(MeasurementDTO measurementDTO) {	 
	    List<SensorData> sensorDataList = measurementDTO.getSensorDataList()
	            .stream()
	            .map(sensorDataMapper::toEntity)
	            .collect(Collectors.toList());
		return new Measurement(measurementDTO.getMeasurementId(),
							   measurementDTO.getSensorApiKey(),
							   sensorDataList,
							   measurementDTO.getMeasurementIsActive());
					
	}
	
	
	

}
