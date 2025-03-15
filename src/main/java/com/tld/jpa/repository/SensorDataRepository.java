package com.tld.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tld.model.SensorData;
import com.tld.model.id.SensorDataId;

public interface SensorDataRepository extends JpaRepository<SensorData,SensorDataId>{

	
}
