package com.tld.mapper;

import com.tld.dto.LocationDTO;
import com.tld.model.City;
import com.tld.model.Company;
import com.tld.model.Location;
import com.tld.model.Users;

public class LocationMapper {
	
	public static LocationDTO mapLocation(Location location) {	
		
		return new LocationDTO(location.getLocationId(),location.getCompany().getCompanyId(), location.getLocationAdress(), location.getCity().getCityId(),
				location.getLocationMeta(), location.getLocationCreatedBy().getUserId(), location.getLocationCreatedAt(), location.getLocationActive(),
				location.getLocationModifiedBy().getUserId(), location.getLocationModifiedAt());		
	}
	
	//metodo comentado por implementacion de seguridad(correjir)
//	public static Location mapLocationDTO(LocationDTO locationDTO) {
//		System.out.println("en mapper "+locationDTO.getLocationActive());
//		
//		return new Location (new Company (locationDTO.getCompanyId()), locationDTO.getLocationAdress(), new City(locationDTO.getCityId()),
//				locationDTO.getLocationMeta(),new Users(locationDTO.getLocationCreatedBy()), locationDTO.getLocationCreatedAt(),
//				locationDTO.getLocationActive(), new Users(locationDTO.getLocationModifiedBy()), locationDTO.getLocationModifiedAt());
//	}

}
