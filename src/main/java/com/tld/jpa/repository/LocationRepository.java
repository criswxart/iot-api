package com.tld.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tld.dto.LocationInfoDTO;
import com.tld.model.Location;


public interface LocationRepository extends JpaRepository<Location, Long>{

	

	 @Query(value = """
	 		    SELECT * FROM get_active_locations (:field, :value);					
		    """, nativeQuery = true)
	 List<LocationInfoDTO> findLocations(@Param("field") String field ,@Param("value") String value);		
	
}
