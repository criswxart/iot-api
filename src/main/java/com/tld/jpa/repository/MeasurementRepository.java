package com.tld.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tld.dto.info.SensorDataInfoDTO;
import com.tld.model.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
	String querySql="""			
					select m.measurement_id as id ,
						   m.sensor_api_key as sensor_api_key, 
						   sn.sensor_name as sensor_name,
						   c.company_name as company_name, 
						   l.location_address as location_adress,
						   ct.city_name as city_name,
						   r.region_name as region_name, 
						   p.country_name as country_name,
						   TO_CHAR( m.sensor_data_created_at, 'DD-MM-YYYY HH24:MI')::VARCHAR(255) AS record_saved_at, 
						   TO_CHAR( m.sensor_data_modified_at, 'DD-MM-YYYY HH24:MI')::VARCHAR(255) AS record_modified_at,
					       m.sensor_data_is_active as is_record_active,	
					       sd.sensor_data_correlative as correlative, 
						   mt.metric_id as metric_id, 
						   mt.metric_name as metric_name, 	
					       sd.sensor_value as metric_value, 
						   sd.sensor_data_created_at as datetime_epoch 
					from measurement m 
					join sensor_data sd on
						   m.measurement_id =sd.measurement_id 
					join metric mt on
						   sd.metric_id=mt.metric_id
					join sensor sn on
						   sn.sensor_api_key = m.sensor_api_key
					join location l on
						   sn.location_id =l.location_id
					join company c on
						   l.company_id =c.company_id
					join city ct on
						   l.city_id =ct.city_id
					join region r  on
						   ct.region_id=r.region_id
					join country p on
						   r.country_id=p.country_id
			""";
	
	  @Query(value = querySql+"""
	  		 WHERE
			 c.company_api_key= :companyApiKey and 
			 m.measurement_id = :id and
			 sn.sensor_api_key =:sensorApiKey ;  		
		""", nativeQuery = true)
	  List<Object[]> findMeasurementById(@Param("id") Long id, @Param("sensorApiKey") String sensorApiKey, @Param("companyApiKey") String companyApiKey);
	

}
