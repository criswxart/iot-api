package com.tld.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tld.dto.LocationInfoDTO;
import com.tld.dto.SensorInfoDTO;
import com.tld.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
	
	Optional<Sensor> findBySensorApiKey(String sensorApiKey);
	
	 @Query(value = """
	 		   select sensor_id, sensor_name, company_name, location_address, city_name, TO_CHAR(sensor_created_at, 'DD-MM-YYYY HH24:MI') AS sensor_created_at, 
				 TO_CHAR(sensor_modified_at, 'DD-MM-YYYY HH24:MI') AS sensor_modified_at,  sensor_is_active 
				from sensor
				join location on
				sensor.location_id =location.location_id
				join city on
				location.city_id= city.city_id
				join company on
				company.company_id =location.company_id
				where
				sensor_id= :sensorId;					
		    """, nativeQuery = true)
	SensorInfoDTO findSensorById(@Param("sensorId") Long sensorId);		

	
	 
	 
		
		

}
