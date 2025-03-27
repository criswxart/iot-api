package com.tld.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tld.dto.SensorDataDTO;
import com.tld.dto.info.SensorDataInfoDTO;
import com.tld.dto.info.SensorInfoDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.SensorDataRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.mapper.SensorDataMapper;
import com.tld.model.Company;
import com.tld.model.Sensor;
import com.tld.model.SensorData;
import com.tld.model.id.SensorDataId;
import com.tld.service.SensorDataService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorDataServiceImpl implements SensorDataService{
	final SensorDataRepository sensorDataRepository;
	final SensorRepository sensorRepository;
	final CompanyRepository companyRepository;
	
	@Override
	public String addSensorData(SensorDataDTO sensorDataDTO) {		
		
		Optional <Sensor> optionalSensor=sensorRepository.findBySensorApiKey(sensorDataDTO.getSensorApiKey());		
		if (optionalSensor.isEmpty()) {
	    	throw new EntityNotFoundException("No existe sensor con la api key entragada, no se grabara data. Valida tu apikey" + sensorDataDTO.getSensorApiKey());
	    }		
		SensorData sensorData=  sensorDataRepository.save( SensorDataMapper.toEntity(sensorDataDTO));
	
		
		
		if(sensorData.getSensorDataId()!=null) {
			//No podemos devolver la ID porque se graba directo en BD, como será un grabado masivo no devolveremos 
			// un sensorDataInfo porque sería contraproducente (el que inserta en un sensor, da igual el mensaje).
			return "Guardado correctamente, hora de grabado "+sensorData.getSensorDataCreatedAt();
		}else {
			return "Error en grabado";
		}
			
	}

	@Override
	public SensorDataInfoDTO updateSensorData(SensorDataDTO sensorDataDTO, String companyApiKey) {		
				
		if (companyRepository.findByCompanyApiKey(companyApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe compania con la api key entregada, NO sepuede actualizar. Valida tu apikey" + companyApiKey);	    
		}
		
		if(sensorRepository.findBySensorApiKey(sensorDataDTO.getSensorApiKey()).isEmpty()) {
			throw new EntityNotFoundException("No existe sensor con la api key entregada en json, NO sepuede actualizar. Valida tu apikey" + sensorDataDTO.getSensorApiKey());
		}
		
		//Validar que exista correlacion entre compania y sensor		
		if(sensorDataRepository.findIfCompanyAndSensorAreOk(companyApiKey, sensorDataDTO.getSensorApiKey())>0 ) {			
			Optional <Sensor> optionalSensor=sensorRepository.findBySensorApiKey(sensorDataDTO.getSensorApiKey());
			if (optionalSensor.isEmpty()) {
				throw new EntityNotFoundException("No existe sensor con la api key entregada en JSON, NO puede actualizar. Valida tu apikey" + sensorDataDTO.getSensorApiKey());	    
			}else {
				Optional<SensorData> optionalSensorData=sensorDataRepository.findById(new SensorDataId(sensorDataDTO.getSensorApiKey(),sensorDataDTO.getSensorDataCorrelative()));				
				if (optionalSensorData.isEmpty()) {
					throw new EntityNotFoundException("No existe un registro con los parametros entregados, revisa tu apiKey y parametros");	    
				}else {
					SensorData sensorData=optionalSensorData.get();
					
					if(!sensorDataDTO.getSensorDataEntry().isEmpty()) {
						sensorData.setSensorEntry(sensorDataDTO.getSensorDataEntry());
					}
					
					if(!(sensorDataDTO.getSensorDataIsActive() == null)) {
						sensorData.setSensorDataIsActive(sensorDataDTO.getSensorDataIsActive());
					}
					
					sensorDataRepository.save(sensorData);
					
					return sensorDataRepository.findSensorDataById(sensorData.getSensorDataId().getSensorCorrelative(),
																   sensorData.getSensorDataId().getSensorApiKey(),companyApiKey).get(0);					
				}
				
			}				
			
		}else {
			throw new EntityNotFoundException("No relacion entre company_api_key del header y el sensor_api_key del json (no estan relacionados)");
		}
		
		
		
		
	}

	@Override
	public SensorDataInfoDTO getSensorDataById(Long correlative, String sensorApiKey, String companyApiKey) {		
		
		if (companyRepository.findByCompanyApiKey(companyApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe compania con la api key entregada, puede actualizar. Valor erroneo: " + companyApiKey);	    
		}
		
		if(sensorRepository.findBySensorApiKey(sensorApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe sensor con la api key entregada como parametro. Valor erroneo: " +sensorApiKey);
		}
		
		if(sensorDataRepository.findIfCompanyAndSensorAreOk(companyApiKey,sensorApiKey)>0 ) {
			return	sensorDataRepository.findSensorDataById(correlative, sensorApiKey, companyApiKey).get(0);
			
		}else {
			throw new EntityNotFoundException("No relacion entre company_api_key del header y el sensor_api_key del json (no estan relacionados)");
		}
	}
	

	@Override
	public List<SensorDataInfoDTO> getSensorDataByEpoch(Long from, Long to, String companyApiKey) {
		if (companyRepository.findByCompanyApiKey(companyApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe compania con la api key entregada, puede actualizar. Valida tu apikey" + companyApiKey);	    
		}			
		return	sensorDataRepository.findSensorDataByEpoch(from, to, companyApiKey);
		
	}

	
	@Override
	public List<SensorDataInfoDTO> getSensorDataByCompany(String companyApiKey) {
		if (companyRepository.findByCompanyApiKey(companyApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe compania con la api key entregada. Dato erroneo: " + companyApiKey);	    
		}			
		return	sensorDataRepository.findSensorDataByCompany(companyApiKey);
	}

	
	


	
	
	@Override
	public SensorDataInfoDTO deleteSensorData(String sensorApiKey, Long sensorDataCorrel, String companyApiKey) {
		if (companyRepository.findByCompanyApiKey(companyApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe compania con la api key entregada, NO se puede actualizar. Valida tu apikey " + companyApiKey);	    
		}
		
		if(sensorRepository.findBySensorApiKey(sensorApiKey).isEmpty()) {
			throw new EntityNotFoundException("No existe sensor con la api key entregada en json, NO se puede actualizar. Valida tu apikey " + sensorApiKey);
		}
		
		//Validar que exista correlacion entre compania y sensor		
		if(sensorDataRepository.findIfCompanyAndSensorAreOk(companyApiKey,sensorApiKey)>0 ) {	
			Optional<SensorData> optionalSensorData=sensorDataRepository.findById(new SensorDataId(sensorApiKey,sensorDataCorrel));
			if (optionalSensorData.isEmpty()) {
				throw new EntityNotFoundException("No existe un registro con los parametros entregados, NO se puede eliminar");	    
			}else {
				SensorData sensorData=optionalSensorData.get();
				sensorData.setSensorDataIsActive(false);
				sensorDataRepository.save(sensorData);				
				return sensorDataRepository.findSensorDataById(sensorDataCorrel,sensorApiKey,companyApiKey).get(0);
			}
			
		}else {
			throw new EntityNotFoundException("No existe relacion entre compania y sensor para que puedas eliminar su "
					+ "registro de entrada de datos. Valida tu apikey" + companyApiKey);
		}
	}

}
