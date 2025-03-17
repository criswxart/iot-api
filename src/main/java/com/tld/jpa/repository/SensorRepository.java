package com.tld.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tld.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
	
	Optional<Sensor> findBySensorApiKey(String sensorApiKey);

}
