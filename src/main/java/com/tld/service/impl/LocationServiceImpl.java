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

@Service
public class LocationServiceImpl implements LocationService{
	
	final LocationRepository locationRepository;
	
	@Autowired
	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository=locationRepository;		
	}
	
	@Override
	public Long addLocation(LocationDTO locationDTO) {	

		Location location= LocationMapper.mapLocationDTO(locationDTO);		
		System.out.println("servicio  "+
						   location.getCity().getCityId()+"  "+
						   location.getLocationCreatedBy().getUserId()+"  "+
						   location.getLocationModifiedBy().getUserId()+"  "+
						   location.getCompany().getCompanyId()+" "+
						   location.getLocationActive().toString());		
    	return locationRepository.save(location).getLocationId();
		
	}

	@Override
	public List<LocationInfoDTO> getLocationById(Long locationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<LocationInfoDTO>> getLocationsByCountryName(String countryName) {		
		return  locationRepository.findByCountryName(countryName);
		
	}

}
