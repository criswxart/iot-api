package com.tld.service;

import org.springframework.stereotype.Service;

import com.tld.dto.LocationDTO;
import com.tld.jpa.repository.LocationRepository;
import com.tld.mapper.LocationMapper;
import com.tld.model.Location;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {
	
	final LocationRepository locationRepository;
	
	public Integer addLocation(LocationDTO locationDTO) {	
		
		Location location= LocationMapper.mapLocationDTO(locationDTO);
		
		System.out.println("servicio  "+
						   location.getCity().getCityId()+"  "+
						   location.getLocationCreatedBy().getUserId()+"  "+
						   location.getLocationModifiedBy().getUserId()+"  "+
						   location.getCompany().getCompanyId()+" "+
						   location.getLocationActive().toString());
		
		return locationRepository.save(location).getLocationId();		
	}

}
