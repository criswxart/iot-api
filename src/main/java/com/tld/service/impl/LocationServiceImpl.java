package com.tld.service.impl;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tld.dto.LocationDTO;
import com.tld.dto.info.LocationInfoDTO;
import com.tld.jpa.repository.UserRepository;
import com.tld.jpa.repository.CityRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.mapper.LocationMapper;
import com.tld.model.City;
import com.tld.model.Location;
import com.tld.model.Users;
import com.tld.service.LocationService;

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
		Location location= LocationMapper.toEntity(locationDTO);	
				    
	    Optional<Users> optionalUser = getAuthenticatedUser();	    
	    
	    location.setLocationCreatedBy(optionalUser.get());
	    location.setLocationModifiedBy(optionalUser.get());		
			
	    //Graba location y luego hace un select custom apra obtener un json decente
	    return locationRepository.findLocations("id",locationRepository.save(location).getLocationId().toString()).get(0) ;
		
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
