package com.tld.service;

import java.util.List;

import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;



public interface LocationService {		
	
	Long addLocation(LocationDTO locationDTO);
	
	LocationInfoDTO updateLocation(Long locationId, LocationDTO locationDTO) ;
	
	LocationInfoDTO getLocationById(Long locationId);
	List<LocationInfoDTO> getLocationsByCountryName(String countryName);
	List<LocationInfoDTO> getAllLocations();
	String deleteLocation(Long locationId);
}
