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
import com.tld.dto.LocationInfoDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.SensorInfoDTO;
import com.tld.jpa.repository.CategoryRepository;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.jpa.repository.UserRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.mapper.SensorMapper;
import com.tld.model.Category;
import com.tld.model.Sensor;
import com.tld.model.Company;
import com.tld.model.Location;
import com.tld.service.SensorService;

import jakarta.persistence.EntityNotFoundException;
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
	public SensorInfoDTO addSensor(SensorDTO sensorDTO) {		
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
			return sensorRepository.findSensorById(id);	
			
		}else {
			throw new EntityNotFoundException("No se pudo grabar, informar a soporte.");
		}
		
		
	}

	@Override
	public SensorInfoDTO updateSensor(SensorDTO sensorDTO) {
		
		Optional<Sensor> optionalSensor=sensorRepository.findById(sensorDTO.getSensorId());
		if (optionalSensor.isEmpty()) {
	    	throw new EntityNotFoundException("No existe sensor por lo que no se actualizara nada");
	    }		
		
		Sensor sensor=optionalSensor.get();
		
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
		
		if(!optionalSensor.get().getSensorApiKey().equals(sensorDTO.getSensorApiKey())) {
			Optional<Sensor> sensorByApiKey= sensorRepository.findBySensorApiKey(sensorDTO.getSensorApiKey());
			if(!sensorByApiKey.isEmpty()) {
				throw new EntityNotFoundException("No puedes grabar esa api key, debes cambiarla");
			}
		}
		
		sensor.setLocation(new Location(sensorDTO.getLocationId()));
		sensor.setSensorName(sensorDTO.getSensorName());
		sensor.setCategory(new Category (sensorDTO.getCategoryId()));
		sensor.setSensorApiKey(sensorDTO.getSensorApiKey());		
		sensor.setSensorIsActive(true);
		
		sensorRepository.save(sensor);
		
		return 	sensorRepository.findSensorById(sensor.getSensorId());	
	}

	@Override
	public List<SensorInfoDTO> getSensors(String field, String value, String companyApiKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteSensor(Long sensorId, String companyApiKey) {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public Optional<SensorDTO> getSensorByApiKey(String companyApiKey) {		
		return sensorRepository.findBySensorApiKey(companyApiKey).map(SensorMapper::toDTO);
	}

		
}
