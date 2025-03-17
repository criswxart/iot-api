package com.tld.mapper;

import com.tld.dto.SensorDTO;
import com.tld.model.Category;
import com.tld.model.Location;
import com.tld.model.Sensor;

public class SensorMapper {
	
	public static SensorDTO toDTO(Sensor sensor) {			
		return new SensorDTO(sensor.getSensorId(),sensor.getLocation().getLocationId(),sensor.getSensorName(),sensor.getCategory().getCategoryId(),
				sensor.getSensorMeta(), sensor.getSensorApiKey());
	}
	
	public static Sensor toEntity(SensorDTO sensorDTO) {		
		return new Sensor(new Location (sensorDTO.getLocationId()), sensorDTO.getSensorName(),new Category(sensorDTO.getCategoryId()),
				sensorDTO.getSensorMeta(), sensorDTO.getSensorApiKey());	
	}	

}
