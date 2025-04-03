package com.tld.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.tld.controller.CompanyController;
import com.tld.dto.MeasurementDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.dto.info.MeasurementInfoDTO;
import com.tld.entity.Measurement;
import com.tld.entity.Metric;
import com.tld.entity.Sensor;
import com.tld.entity.SensorData;
import com.tld.exception.InvalidApiKeyException;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.MeasurementRepository;
import com.tld.jpa.repository.MetricRepository;
import com.tld.jpa.repository.SensorDataRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.mapper.MeasurementMapper;
import com.tld.service.MeasurementService;
import com.tld.util.LogUtil;
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
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud recibida en impl addSensorData");	
		
		if(sensorApiKey==null || sensorApiKey.isEmpty() ) {
			if(measurementDTO.getApi_Key()==null) {
				throw new InvalidApiKeyException ("Sensor / Api key vacio");
			}
		}else {
			measurementDTO.setApi_Key(sensorApiKey);
		}
		
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud tiene apikey "+measurementDTO.getApi_Key());	
		
		//Busco sensor
		Sensor sensor = sensorRepository.findBySensorApiKey(measurementDTO.getApi_Key())
						.orElseThrow(() -> new com.tld.exception.EntityNotFoundException("Sensor / Api key no encontrado."));
		
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Sensor valido, id: "+sensor.getSensorId());
		Measurement measurement = new Measurement();
		measurement.setSensor(sensor);		
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Entidad Measurement creado y seteado");
		Measurement savedMeasurement = measurementRepository.save(measurement);
		Long measurementId = savedMeasurement.getMeasurementId();	
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Measurement insertado, id: "+measurementId);		
		
		
		List<SensorData> sensorDataList = new ArrayList<>();;
		
		for(int i=0; i < measurementDTO.getJson_data().size(); i++ ) {			
			SensorDataDTO sensorDataDTO = measurementDTO.getJson_data().get(i);			
			Map<String, Object> sensorDataMap = sensorDataDTO.getSensorData();
			
			for (String key : sensorDataMap.keySet()) {
			    String metricName = key;
			    LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Buscando metrica");
			    Metric metric = metricRepository.findByMetricName(metricName)
			            .orElseGet(() -> {
			                Metric newMetric = new Metric();
			                newMetric.setMetricName(metricName);
			                LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Se grabo metrica, id: "+newMetric.getMetricId());
			                return metricRepository.save(newMetric);
			            });	
			    LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Armando un sensor_data");
			    SensorData sensorData = new SensorData(
			    		measurement,//aca va la tabla "padre" o "cabecera"
			    		null, //correlativo null porque lo asigno al final
			            metric, //Va la entidad metric y ahi esta id y nombre
			            (Double) sensorDataMap.get(metricName),  //valores de la metrica
			            sensorDataDTO.getDatetime()  //valor Long que envian en el json
			    );
			    sensorDataList.add(sensorData);
			    LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Sensor_data agregado a lista");
			}
		}
		
		int correlativo=1;
		//Seteo correlativo de detalle, asi queda ordenado y sabremos facil cuantos registros tuvo cada Measurement
		for (SensorData dto:sensorDataList) {
			LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Seteando correlativos "+correlativo);
			dto.getSensorDataId().setSensorDataCorrelative(correlativo);			
			sensorDataRepository.save(dto);
			LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Sensor_data id "+correlativo+" ingresado a BD");
			correlativo++;
		}
		
		measurementDTO.setMeasurementId(measurementId);
		measurementDTO.setSensorId(sensor.getSensorId());
		measurementDTO.setMeasurementIsActive(true);
		return measurementDTO;
	}

	@Override
	public MeasurementInfoDTO updateSensorData (String sensorApiKey, Long measurementId, String companyApiKey) {
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud recibida en impl updateSensorData");
		if(measurementRepository.findIfCompanyAndSensorAreOk(companyApiKey, sensorApiKey)==0) {
			throw new com.tld.exception.InvalidCompanySensorException("Solo puedes modificar cuando compania y sensor estan relacionados");
		}	
		
		Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementId);
		if(optionalMeasurement.isEmpty()) {
			throw new com.tld.exception.EntityNotFoundException("No existe registro para poder inactivar");
		}
		
		Measurement measurement = optionalMeasurement.get();
		measurement.setMeasurementIsActive(true);		
		measurementRepository.save(measurement);		
		
		return measurementMapper.mapToMeasurementInfoDTO( measurementRepository.findMeasurementById(measurementId,sensorApiKey,companyApiKey));
	}


	@Override
	public List<MeasurementInfoDTO> getSensorDataByEpoch(Long from, Long to, String companyApiKey) {
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud recibida en impl getSensorDataByEpoch");
		List<Object[]> info = measurementRepository.findMeasurementDataByEpoch(from, to, companyApiKey);
		if(info.isEmpty()) {
			throw new com.tld.exception.EntityNotFoundException("No hay resultados para la compania");
		}
		return measurementMapper.mapToListMeasurementInfoDTO(info);
	}

	@Override
	public List<MeasurementInfoDTO> getSensorDataByCompany(String companyApiKey) {
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud recibida en impl getSensorDataByCompany");
		List<Object[]> info = measurementRepository.findMeasurementDataByCompany(companyApiKey);
		if(info.isEmpty()) {
			throw new com.tld.exception.EntityNotFoundException("No hay resultados para la compania");
		}
		return measurementMapper.mapToListMeasurementInfoDTO(info);
	}

	@Override
	public MeasurementInfoDTO getSensorDataById(Long measurementID, String sensorApiKey, String companyApiKey) {	
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud recibida en impl getSensorDataById");
		List<Object[]> info = measurementRepository.findMeasurementById(measurementID, sensorApiKey, companyApiKey);
		if(info.isEmpty()) {
			throw new com.tld.exception.EntityNotFoundException("No hay resultados para la compania / sensor");
		}
		return measurementMapper.mapToMeasurementInfoDTO(info);
	}

	@Override
	public MeasurementInfoDTO deleteSensorData(String sensorApiKey, Long measurementID, String companyApiKey) {
		LogUtil.log(MeasurementServiceImpl.class, Level.INFO, "Solicitud recibida en impl deleteSensorData");
		if(measurementRepository.findIfCompanyAndSensorAreOk(companyApiKey, sensorApiKey)==0) {
			throw new com.tld.exception.InvalidCompanySensorException("Solo puedes modificar cuando compania y sensor estan relacionados");
		}	
		
		Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementID);
		if(optionalMeasurement.isEmpty()) {
			throw new com.tld.exception.EntityNotFoundException("No existe registro para poder inactivar");
		}
		
		Measurement measurement = optionalMeasurement.get();
		measurement.setMeasurementIsActive(false);		
		measurementRepository.save(measurement);		
		
		return measurementMapper.mapToMeasurementInfoDTO( measurementRepository.findMeasurementById(measurementID,sensorApiKey,companyApiKey));
	}

}
