package com.tld.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tld.dto.CompanyDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.info.LocationInfoDTO;
import com.tld.dto.info.SensorInfoDTO;
import com.tld.entity.Category;
import com.tld.entity.Company;
import com.tld.entity.Location;
import com.tld.entity.Sensor;
import com.tld.jpa.repository.CategoryRepository;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.jpa.repository.UserRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.mapper.SensorMapper;
import com.tld.service.SensorService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService{
	
	final SensorRepository sensorRepository;
	final LocationRepository locationRepository;
	final CompanyRepository companyRepository;
	final UserRepository userRepository;
	final CategoryRepository categoryRepository;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.of("America/Santiago"));
	
	 
	@Override
	public String addSensor(SensorDTO sensorDTO) {		
		//Falta validar que la direccion seleccionada pertenezca a la misma empresa
		//Sacar una lista de direcciones con la company_id  y luego ver que la ingresada
		//este en el listado	
		
		Optional<Company> optionalCompany =companyRepository.findByCompanyApiKey(sensorDTO.getSensorApiKey());
		if (optionalCompany.isEmpty()) {
	    	throw new EntityNotFoundException("No existe la compania con la api key entragada, no se grabara sensor. Valida tu apikey" + sensorDTO.getSensorApiKey());
	    }
		
		if (locationRepository.findIfLocationAndCompanyAreOk(optionalCompany.get().getCompanyId(),sensorDTO.getLocationId())<1){
			throw new EntityNotFoundException("No existe tal direccion asociada a la compañia, no se ingresara sensor");
		}
		
		Optional<Category> optionalCategory=categoryRepository.findById(sensorDTO.getCategoryId());
		if (optionalCategory.isEmpty()) {
	    	throw new EntityNotFoundException("No existe la categoria ingresada:" + sensorDTO.getCategoryId());
	    }		
		Sensor sensor=SensorMapper.toEntity(sensorDTO);		
		
		Long id= sensorRepository.save(sensor).getSensorId();
		
		if(id>0) {
			return("id grabada"+id);
			//return sensorRepository.findSensors("id", sensor.getSensorId().toString(),sensorDTO.getSensorApiKey()).get(0);			
		}else {
			throw new EntityNotFoundException("No se pudo grabar, informar a soporte.");
		}
		
		
	}

	@Override
	public SensorInfoDTO updateSensor(String companyApiKey, SensorDTO sensorDTO) {
		
		Optional<Sensor> optionalSensor=sensorRepository.findById(sensorDTO.getSensorId());
		if (optionalSensor.isEmpty()) {
	    	throw new EntityNotFoundException("No existe sensor por lo que no se actualizara nada");
	    }		
		
		Sensor sensor=optionalSensor.get();
		
		//Se valida con api_key del header, en el json puede venir otro para hacer el update.
		Optional<Company> optionalCompany =companyRepository.findByCompanyApiKey(companyApiKey);
		if (optionalCompany.isEmpty()) {
	    	throw new EntityNotFoundException("No existe la compania con la api key entragada, no se grabara sensor. Valida tu apikey" + sensorDTO.getSensorApiKey());
	    }
		
		
		if (locationRepository.findIfLocationAndCompanyAreOk(optionalCompany.get().getCompanyId(),sensorDTO.getLocationId())<1){
			throw new EntityNotFoundException("No existe tal direccion asociada a la compañia, no se ingresara sensor");
		}else {
			sensor.setLocation(new Location(sensorDTO.getLocationId()));
		}
		
		
		if(sensorDTO.getCategoryId()!=null) {
			Optional<Category> optionalCategory=categoryRepository.findById(sensorDTO.getCategoryId());
			if (optionalCategory.isEmpty()) {
		    	throw new EntityNotFoundException("No existe la categoria ingresada:" + sensorDTO.getCategoryId());
		    }else {
		    	sensor.setCategory(new Category (sensorDTO.getCategoryId()));	    
		    }			
		}
				
		
		if((Optional.ofNullable(sensorDTO.getSensorApiKey()).map(String::length).orElse(0)>0)&&(!sensor.getSensorApiKey().equals(sensorDTO.getSensorApiKey()))){
			Optional<Sensor> sensorByApiKey= sensorRepository.findBySensorApiKey(sensorDTO.getSensorApiKey());
			if(!sensorByApiKey.isEmpty()) {
				throw new EntityNotFoundException("No puedes grabar esa api key, debes cambiarla");
			}else {
				//Si existe api key en el json y es diferente a la que ya tiene asociada (la que venia en el header)
				//se valida que no exista asociada a otro company. Si no existe en la tabla se hara un update
				//incluyendo el nuevo api_key (si el api key viene en el json y es igual al asociado no se hara nada)
				sensor.setSensorApiKey(sensorDTO.getSensorApiKey());
			}
		}
		
		if(Optional.ofNullable(sensorDTO.getSensorName()).map(String::length).orElse(0)>0) {
			sensor.setSensorName(sensorDTO.getSensorName());
		}		
					
		sensor.setSensorIsActive(true);
		
		sensorRepository.save(sensor);
		
		return sensorRepository.findSensors("id", sensor.getSensorId().toString(),companyApiKey).get(0);
		//return 	sensorRepository.findSensorById(sensor.getSensorId());	
	}

	@Override
	public List<SensorInfoDTO> getSensors(String field, String value, String companyApiKey) {	
		
		List<SensorInfoDTO> resultado= sensorRepository.findSensors(field, value, companyApiKey);
		
		if (resultado.isEmpty()) {
			throw new EntityNotFoundException("No hay resultados, 2 posibles razones: no existe o intentas ver informacion de otra compañia");
		}
		return resultado;
		
	}

	@Override
	public String deleteSensor(Long sensorId, String companyApiKey) {
		String mensaje;
		Optional<Sensor> optionalSensor= sensorRepository.findById(sensorId);
		if(optionalSensor.isEmpty()) {
			mensaje= "No puedes borrar lo que nunca existio";
		}else {		
			Sensor sensor=optionalSensor.get();
			if (sensor.getSensorApiKey().equals(companyApiKey)) {
				sensor.setSensorIsActive(false);
				sensorRepository.save(sensor);
				mensaje= "Has inactivado el sensor";
			}else {
				mensaje= "Estas intentando inactivar el sensor de otra campania o tu apiKey es invalida accion no valida";
			}
		}
		return mensaje;
	}
		
	/*@Override
	public Optional<SensorDTO> getSensorByApiKey(String companyApiKey) {		
		return sensorRepository.findBySensorApiKey(companyApiKey).map(SensorMapper::toDTO);
	}*/

		
}
