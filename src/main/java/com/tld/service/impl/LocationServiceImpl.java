package com.tld.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.jpa.repository.LocationRepository;
import com.tld.mapper.LocationMapper;
import com.tld.model.Location;
import com.tld.service.LocationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{
	
	final LocationRepository locationRepository;
	

	@Override
	public Long addLocation(LocationDTO locationDTO) {	

		Location location= LocationMapper.toEntity(locationDTO);	
    	return locationRepository.save(location).getLocationId();
		
	}

	@Override
	public List<LocationInfoDTO> getLocationById(Long locationId) {
		return null;
	}

	@Override
	public List<List<LocationInfoDTO>> getLocationsByCountryName(String countryName) {		
		return  locationRepository.findByCountryName(countryName);		
	}

}
