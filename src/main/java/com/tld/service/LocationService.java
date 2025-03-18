package com.tld.service;

import java.util.List;
import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;



public interface LocationService {		
	
	Long addLocation(LocationDTO locationDTO);	
	LocationInfoDTO updateLocation(Long locationId, LocationDTO locationDTO) ;	
	List<LocationInfoDTO> getLocations(String field, String value);
	String deleteLocation(Long locationId);
}
