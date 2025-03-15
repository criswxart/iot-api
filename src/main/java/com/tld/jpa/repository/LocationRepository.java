package com.tld.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tld.dto.LocationInfoDTO;
import com.tld.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{
	
	 @Query(value = """
		        SELECT 
		            c.company_name AS companyName, 
		            l.location_adress AS locationAddress, 
		            ct.city_name AS cityName, 
		            r.region_name AS regionName, 
		            co.country_name AS countryName
		        FROM location l
		        JOIN company c ON l.company_id = c.company_id
		        JOIN city ct ON l.city_id = ct.city_id
		        JOIN region r ON ct.region_id = r.region_id
		        JOIN country co ON r.country_id = co.country_id
		        WHERE LOWER(co.country_name) LIKE LOWER(CONCAT('%', :countryName, '%'))
		    """, nativeQuery = true)
	  List<List<LocationInfoDTO>> findByCountryName(@Param("countryName") String countryName);
	

}
