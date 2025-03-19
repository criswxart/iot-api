package com.tld.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tld.dto.CompanyDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.SensorInfoDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.jpa.repository.SensorRepository;
import com.tld.jpa.repository.UserRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.mapper.SensorMapper;
import com.tld.model.Category;
import com.tld.model.City;
import com.tld.model.Location;
import com.tld.model.Sensor;
import com.tld.model.Company;
import com.tld.model.Users;
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

	@Override
	public Long addSensor(SensorDTO sensorDTO) {		
		//Falta validar que la direccion seleccionada pertenezca a la misma empresa
		//Sacar una lista de direcciones con la company_id  y luego ver que la ingresada
		//este en el listado	
		
		Optional<Company> optionalCompany =companyRepository.findByCompanyApiKey(sensorDTO.getSensorApiKey());
		if (optionalCompany.isEmpty()) {
	    	throw new EntityNotFoundException("No existe la compania, no se grabara sensor. Valida tu apikey" + sensorDTO.getSensorApiKey());
	    }
		
		Sensor sensor=SensorMapper.toEntity(sensorDTO);		
		
		return sensorRepository.save(sensor).getSensorId();
	}

	@Override
	public SensorInfoDTO updateSensor(Long sensorId, SensorDTO sensorDTO) {
		
		//validar company_api_key
		Optional<Sensor> optionalSensor = sensorRepository.findById(sensorId);
		if (optionalSensor.isEmpty()) {
	    	throw new EntityNotFoundException("Location no encontrada con ID: " + sensorId);
	    }
		
		Sensor sensor = optionalSensor.get();	
		
		/*
		Optional<Company> optionalCompany = companyRepository.findByCompanyApiKey(sensorId);
		if (optionalSensor.isEmpty()) {
	    	throw new EntityNotFoundException("Location no encontrada con ID: " + sensorId);
	    }
		
		

		if(sensorDTO.getLocationId()!=null) {
			Optional<Location> optionalLocation = locationRepository.findById(sensorDTO.getLocationId());			
			sensor.setLocation(new Location(sensorDTO.getLocationId()));
		}
		
		
		
		if (optionalLocation.isEmpty()) {
	    	throw new EntityNotFoundException("Location no encontrada con ID: " + sensorId);
	    }
		
		
		
		if(sensorDTO.getSensorName()!=null) {			
			sensor.setSensorName(sensorDTO.getSensorName());		
		}
		
		if(sensorDTO.getCategoryId()!=null) {
			sensor.setCategory(new Category(sensorDTO.getCategoryId()));
		}
		
		if(sensorDTO.getSensorMeta()!=null) {			
			sensor.setSensorMeta(sensorDTO.getSensorMeta());	
		}
		
		if(sensorDTO.getSensorApiKey()!=null) {			
			sensor.setSensorApiKey(sensorDTO.getSensorApiKey());		
		}	
				
		sensor.setSensorIsActive(true);
		
		sensorRepository.save(sensor);
		
		//Falta repositorio categoria, region, pais para obtener campos por ID
		return new SensorInfoDTO(
				sensor.getSensorName(),
				"nombre compañia",
				""
				private String sensorName;
				private String companyName;
			    private String locationAddress;
			    private String cityName;          
				private Instant sensorCreatedAt;
				private Instant sensorModifiedAt;
				private Boolean sensorIsActive;
		    );	*/
		return null;
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
