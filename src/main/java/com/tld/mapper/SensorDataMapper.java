package com.tld.mapper;
import com.tld.dto.SensorDataDTO;
import com.tld.model.SensorData;

public class SensorDataMapper {
	  public static SensorDataDTO toDTO(SensorData sensorData) {
	        return new SensorDataDTO(sensorData.getSensorDataId().getSensorApiKey(),sensorData.getSensorDataId().getSensorCorrelative(),
	        		sensorData.getSensorEntry(), sensorData.getSensorDataIsActive() );	        
	    }

	    public static SensorData toEntity(SensorDataDTO sensorDataDTO) {	    	
	    	return new SensorData(sensorDataDTO.getSensorApiKey(),sensorDataDTO.getSensorDataCorrelative(),sensorDataDTO.getSensorDataEntry());	
	    }

}
