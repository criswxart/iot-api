package com.tld.service.impl;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tld.controller.CompanyController;
import com.tld.dto.LocationDTO;
import com.tld.dto.info.LocationInfoDTO;
import com.tld.entity.City;
import com.tld.entity.Location;
import com.tld.entity.Users;
import com.tld.jpa.repository.UserRepository;
import com.tld.jpa.repository.CityRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.mapper.LocationMapper;
import com.tld.service.LocationService;
import com.tld.util.LogUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
	
	final LocationRepository locationRepository;
	final CityRepository cityRepository;
	final UserRepository userRepository;
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.of("America/Santiago"));
	

	@Override
	public LocationInfoDTO addLocation(LocationDTO locationDTO) {	
		LogUtil.log(LocationServiceImpl.class, Level.INFO, "Solicitud recibida en impl addLocation");
		Location location= LocationMapper.toEntity(locationDTO);					    
		Users user = getAuthenticatedUser().orElseThrow(() -> new com.tld.exception.InvalidUserException("Usuario no valido"));   
	    
	    location.setLocationCreatedBy(user);
	    location.setLocationModifiedBy(user);		
	    
	    
	    try {	    	
	    	Location savedLocation = locationRepository.save(location);
	    	LogUtil.log(LocationServiceImpl.class, Level.INFO, "Retornando location almacenada");   
			return    locationRepository.findLocations("id", savedLocation.getLocationId().toString())
					    	            .stream()
					    	            .findFirst()
					    	            .orElseThrow(() -> new com.tld.exception.EntityNotFoundException("No se encontro la location recien almacenada"));	
		} catch (DataIntegrityViolationException e) {
			String message = e.getMostSpecificCause().getMessage().toLowerCase();
			if (message.contains("llave duplicada") || message.contains("duplicate key")) {
		        throw new com.tld.exception.UniqueConstraintViolationException("No se puede ingresar la misma direccion mas de una vez por compania.");
		    }
		    throw new com.tld.exception.CustomDatabaseException("Error de integridad de datos", e);			
		}	
	 
	}


	
	@Override
	public LocationInfoDTO updateLocation(Long locationId, LocationDTO locationDTO) {			
		
		Optional<Location> optionalLocation = locationRepository.findById(locationId);
		if (optionalLocation.isEmpty()) {
	    	throw new EntityNotFoundException("Location no encontrada con ID: " + locationId);
	    }
		
		Location location = optionalLocation.get();
		
		Optional<Users> optionalUser = getAuthenticatedUser();	     
	    location.setLocationModifiedBy(optionalUser.get());	
		
		
		
		if(locationDTO.getLocationAddress()!=null) {
			location.setLocationAddress(locationDTO.getLocationAddress());
		}
		
		if(locationDTO.getCityId()!=null) {				
			City city =	cityRepository.getReferenceById(locationDTO.getCityId());
			location.setCity(city);			
		}
		
		if(locationDTO.getLocationMeta()!=null) {
			location.setLocationMeta(locationDTO.getLocationMeta());
		}
		
		
		location.setLocationIsActive(true);
		
		locationRepository.save(location);
		
		return locationRepository.findLocations("id",location.getLocationId().toString()).get(0) ;		
				
	}	

	@Override
	public List<LocationInfoDTO> getLocations(String field, String value) {		
		return  locationRepository.findLocations(field, value);		
	}
	
	@Override
	public String deleteLocation(Long locationId) {			
		
		Optional <Location> optionalLocation=locationRepository.findById(locationId);
		
		if (optionalLocation.isEmpty()) {
			throw new EntityNotFoundException("No existe ninguna location con id: " + locationId);
		}		
	
		Location location= optionalLocation.get();
		
		Optional<Users> optionalUser = getAuthenticatedUser();	  
	    location.setLocationModifiedBy(optionalUser.get());	
		
		if(!optionalLocation.get().getLocationIsActive()) {
			return "El registro ya esta inactivo, fue hecho por "+location.getLocationModifiedBy().getUserName()+" a las "+ new String(location.getLocationModifiedAt()
	        	    .atZone(ZoneId.of("America/Santiago"))
	        	    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		}
		
		location.setLocationIsActive(false);
		locationRepository.save(location);
		return "Se inactiva registro de direccion: "+location.getLocationAddress()+" por: "+location.getLocationModifiedBy().getUserName() 
				+" a las "+new String(location.getLocationModifiedAt()
		        	    .atZone(ZoneId.of("America/Santiago"))
		        	    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
	}
	
	 private Optional<Users> getAuthenticatedUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication != null && authentication.isAuthenticated()) {
	            String userName = authentication.getName();
	            return userRepository.findByUserName(userName);
	        }
	        return Optional.empty();
	    }

}
