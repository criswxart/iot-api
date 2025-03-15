package com.tld.mapper;

import com.tld.dto.LocationDTO;
import com.tld.model.City;
import com.tld.model.Company;
import com.tld.model.Location;
import com.tld.model.Users;

public class LocationMapper {
	
	public static LocationDTO toDTO(Location location) {			
		return new LocationDTO(location.getLocationId(),location.getCompany().getCompanyId(), location.getLocationAdress(), location.getCity().getCityId(),
				location.getLocationMeta(), location.getLocationCreatedBy().getUserId(), 
				location.getLocationModifiedBy().getUserId());		
	}
	
	//
	public static Location toEntity(LocationDTO locationDTO) {		
		return new Location (new Company (locationDTO.getCompanyId()), locationDTO.getLocationAdress(), new City(locationDTO.getCityId()),				
				locationDTO.getLocationMeta(),new Users(locationDTO.getLocationCreatedBy()),
				new Users(locationDTO.getLocationModifiedBy()));
		
	}

}
