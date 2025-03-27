package com.tld.mapper;


import com.tld.dto.SensorDataDTO;
import com.tld.model.Metric;
import com.tld.model.SensorData;

public class SensorDataMapper {	

    public SensorDataDTO toDTO(SensorData sensorData) { 	
    	
        SensorDataDTO sensorDataDTO = new SensorDataDTO();
        sensorDataDTO.setMetricName(sensorData.getMetric().getMetricName());  // Obtener nombre de la métrica
        sensorDataDTO.setSensorDataValue(sensorData.getSensorDataValue());
        sensorDataDTO.setSensorDataDateTime(sensorData.getSensorDataDateTime());
        return sensorDataDTO;
    }

    public SensorData toEntity(SensorDataDTO sensorDataDTO) {
        SensorData sensorData = new SensorData();      
        sensorData.setMetric(new Metric(sensorDataDTO.getMetricName()));
        sensorData.setSensorDataValue(sensorDataDTO.getSensorDataValue());
        sensorData.setSensorDataDateTime(sensorDataDTO.getSensorDataDateTime());
        return sensorData;
    }
	/*  public static SensorDataDTO toDTO(SensorData sensorData) {
	        return new SensorDataDTO(sensorData.getSensorDataId().getSensorApiKey(),sensorData.getSensorDataId().getSensorCorrelative(),
	        		sensorData.getSensorEntry(), sensorData.getSensorDataIsActive() );	        
	    }

	    public static SensorData toEntity(SensorDataDTO sensorDataDTO) {	    	
	    	return new SensorData(sensorDataDTO.getSensorApiKey(),sensorDataDTO.getSensorDataCorrelative(),sensorDataDTO.getSensorDataEntry());	
	    }
*/
}
