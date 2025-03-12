package com.tld.service;

import java.util.List;

import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;



public interface LocationService {		
	
	Integer addLocation(LocationDTO locationDTO) ;
	
	List<LocationInfoDTO> getLocationById(Integer locationId);
	List<List<LocationInfoDTO>> getLocationsByCountryName(String countryName);

}
