package com.tld.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tld.dto.LocationInfoDTO;
import com.tld.model.Location;


public interface LocationRepository extends JpaRepository<Location, Long>{

	

	 @Query(value = """
	 		    select  company.company_name, location.location_address, city.city_name,
						region.region_name, country.country_name, location.location_meta,
						c.user_name as userNameC ,m.user_name as userNameM 
				from location
				join company on
					company.company_id =location.company_id
				join city on
					city.city_id =location.city_id 
				join region on
					region.region_id =city.region_id  
				join country on
					country.country_id =region.country_id
				join users c on
					c.user_id =location.location_created_by
				join users m on
					m.user_id =location.location_modified_by
				where
					location.location_is_active = true and
					LOWER(country_name) LIKE LOWER(CONCAT('%', :countryName , '%'))
		    """, nativeQuery = true)
	 List<LocationInfoDTO> findByCountryName(@Param("countryName") String countryName);
	
	 
	 @Query(value = """
  		       select   company.company_name, location.location_address, city.city_name,
						region.region_name, country.country_name, location.location_meta,
						c.user_name as userNameC ,m.user_name as userNameM
				from location
				join company on
					company.company_id =location.company_id
				join city on
					city.city_id =location.city_id 
				join region on
					region.region_id =city.region_id  
				join country on
					country.country_id =region.country_id
				join users c on
					c.user_id =location.location_created_by
				join users m on
					m.user_id =location.location_modified_by
				where
					location.location_id=:locationId;
		    """, nativeQuery = true)
	 LocationInfoDTO findLocationById(@Param("locationId") Long locationId);
	 
	 
	 
	 @Query(value = """
	 		 	select  company.company_name, location.location_address, city.city_name,
						region.region_name, country.country_name, location.location_meta,
						c.user_name as userNameC ,m.user_name as userNameM
				from location
				join company on
					company.company_id =location.company_id
				join city on
					city.city_id =location.city_id 
				join region on
					region.region_id =city.region_id  
				join country on
					country.country_id =region.country_id
				join users c on
					c.user_id =location.location_created_by
				join users m on
					m.user_id =location.location_modified_by
				where
					location.location_is_active = true	
	 		
	 		   """, nativeQuery = true)	 	
	 List<LocationInfoDTO>findAllLocation();	 	 	 
	 
	
}
