package com.tld.mapper;

import com.tld.dto.LocationDTO;
import com.tld.model.City;
import com.tld.model.Company;
import com.tld.model.Location;
import com.tld.model.Users;

public class LocationMapper {
	
	public static LocationDTO toDTO(Location location) {			
		return new LocationDTO(location.getLocationId(),location.getCompany().getCompanyId(), location.getLocationAddress(), location.getCity().getCityId(),
				location.getLocationMeta());		
	}
	
	//
	public static Location toEntity(LocationDTO locationDTO) {		
		return new Location (new Company (locationDTO.getCompanyId()), 
							locationDTO.getLocationAddress(), 
							new City(locationDTO.getCityId()),				
							locationDTO.getLocationMeta());
		
	}

}
