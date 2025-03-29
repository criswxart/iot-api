package com.tld.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.tld.dto.MeasurementDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.dto.info.MeasurementInfoDTO;
import com.tld.dto.info.SensorDataInfoDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.MeasurementRepository;
import com.tld.jpa.repository.MetricRepository;
import com.tld.jpa.repository.SensorDataRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.mapper.MeasurementMapper;
import com.tld.mapper.SensorDataMapper;
import com.tld.model.Measurement;
import com.tld.model.Metric;
import com.tld.model.Sensor;
import com.tld.model.SensorData;
import com.tld.model.id.SensorDataId;
import com.tld.service.MeasurementService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService{

	final SensorDataRepository sensorDataRepository;
	final SensorRepository sensorRepository;
	final CompanyRepository companyRepository;
	final MeasurementRepository measurementRepository;
	final MetricRepository metricRepository;
	final MeasurementMapper measurementMapper;
	
	
	@Override
	public MeasurementDTO addSensorData(MeasurementDTO measurementDTO, String sensorApiKey) {
		
		if(sensorApiKey.isEmpty()) {
			if(measurementDTO.getApi_key().isEmpty()) {}
			 new RuntimeException("No ha entregado sensorApiKey, no se procesa solicitud");
		}else {
			measurementDTO.setApi_key(sensorApiKey);
		}
				
		//Busco sensor
		Sensor sensor = sensorRepository.findBySensorApiKey(measurementDTO.getApi_key())
						.orElseThrow(() -> new RuntimeException("Sensor no encontrado, no se puede procesar solicitud."));

		Measurement measurement = new Measurement();
		measurement.setSensor(sensor);	
		
		Measurement savedMeasurement = measurementRepository.save(measurement);
		Long measurementId = savedMeasurement.getMeasurementId();	
		
		List<SensorData> sensorDataList = new ArrayList<>();;
		
		for(int i=0; i < measurementDTO.getJson_data().size(); i++ ) {			
			SensorDataDTO sensorDataDTO = measurementDTO.getJson_data().get(i);			
			Map<String, Object> sensorDataMap = sensorDataDTO.getSensorData();
			
			for (String key : sensorDataMap.keySet()) {
			    String metricName = key;
			    Metric metric = metricRepository.findByMetricName(metricName)
			            .orElseGet(() -> {
			                Metric newMetric = new Metric();
			                newMetric.setMetricName(metricName);
			                return metricRepository.save(newMetric);
			            });		
			    SensorData sensorData = new SensorData(
			    		measurement,
			    		null,
			            metric,//pasamos la entidad, donde esta la ID y nombre
			            (Double) sensorDataMap.get(metricName),  //obtenemos el valor Duble del map, busco por nombre ej: temperatura y obtengo su valor
			            sensorDataDTO.getDatetime() //Guardo el valor en long. Se podria eventualmente grabar en BD como instant
			    );
			    /*
			    System.out.println("antes de añadir a lista, valores: "+sensorData.getSensorDataId().getMeasurementId()+"/"+
			    sensorData.getSensorDataId().getMetricId()+"/"+			    		
			    sensorData.getSensorDataId().getSensorDataCorrelative()+"/"+	
			    sensorData.getSensorDataValue()+"/"+
			    sensorData.getSensorDataDateTime()
			    		);*/
			    sensorDataList.add(sensorData);
			}
		}
		
		int correlativo=1;
		//Seteo correlativo de detalle, asi queda ordenado y sabremos facil cuantos registros tuvo cada Measurement
		for (SensorData dto:sensorDataList) {			
			dto.getSensorDataId().setSensorDataCorrelative(correlativo);			
			sensorDataRepository.save(dto);
			correlativo++;
		}
		
		measurementDTO.setMeasurementId(measurementId);
		return measurementDTO;
	}

	@Override
	public SensorDataInfoDTO updateSensorData(MeasurementDTO measurementDTO, String companyApiKey) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SensorDataInfoDTO> getSensorDataByEpoch(Long field, Long value, String companyApiKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SensorDataInfoDTO> getSensorDataByCompany(String companyApiKey) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MeasurementInfoDTO getSensorDataById(Long measurementID, String sensorApiKey, String companyApiKey) {
		List<Object[]> info = measurementRepository.findMeasurementById(measurementID, sensorApiKey, companyApiKey);
	    MeasurementInfoDTO measurementInfoDTO = new MeasurementInfoDTO();
	    
	    Object[] general = info.get(0);
	    measurementInfoDTO.setId((Long)general[0]);
	    measurementInfoDTO.setSensor_api_key((String) general[1]);
	    measurementInfoDTO.setSensor_name((String) general[2]);
	    measurementInfoDTO.setCompany_name((String) general[3]);
	    measurementInfoDTO.setLocation_adress((String) general[4]);
	    measurementInfoDTO.setCity_name((String) general[5]);
	    measurementInfoDTO.setRegion_name((String) general[6]);
	    measurementInfoDTO.setCountry_name((String) general[7]);
	    measurementInfoDTO.setRecord_saved_at((String) general[8]);
	    measurementInfoDTO.setRecord_modified_at((String) general[9]);
	    measurementInfoDTO.setIs_record_active((Boolean) general[10]);
	   
	    
        List<SensorDataInfoDTO> sensorDataInfoList = new ArrayList<>();
        
        for (Object[] row : info) {       	
        	
            SensorDataInfoDTO sensorDataInfoDTO= new SensorDataInfoDTO();
            sensorDataInfoDTO.setCorrelative((Integer) row[11]);
            sensorDataInfoDTO.setMetric_id((Integer) row[12]);
            sensorDataInfoDTO.setMetric_name((String) row[13]);
            sensorDataInfoDTO.setMetric_value((Double) row[14]);
            sensorDataInfoDTO.setDatetime_epoch((Long) row[15]);
            sensorDataInfoDTO.setDatetime_legible(Instant.ofEpochMilli((Long) row[15]));            
            sensorDataInfoList.add(sensorDataInfoDTO);             
        	
        }
        measurementInfoDTO.setRecords(sensorDataInfoList);
        
		return measurementInfoDTO;
	}

	@Override
	public SensorDataInfoDTO deleteSensorData(String sensorApiKey, Long measurementID, String companyApiKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
