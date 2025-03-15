package com.tld.controller;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.SensorDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.service.SensorDataService;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/sensordata")
@RequiredArgsConstructor
public class SensorDataController {
	
	private final SensorDataService sensorDataService;	
	
	private final SensorService sensorService;
	
	
	@PostMapping("add")
	public ResponseEntity <?> addSensorData(@RequestBody SensorDataDTO sensorDataDTO, @RequestHeader("sensor_api_key") String sensorApiKey){
		System.out.println("apiKey "+sensorApiKey+" SensorDTO"+ sensorDataDTO.getSensorEntry());
		sensorDataDTO.setSensorApiKey(sensorApiKey);
		sensorDataDTO.setSensorCorrelative(null);
		
		Optional<SensorDTO> sensorDTO =sensorService.getSensorByApiKey(sensorApiKey);		
		
		if(sensorDTO.isEmpty()){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No tienes una llave valida");
		}		
		
		System.out.println(sensorDTO.get().getSensorName());
		try {
			return new  ResponseEntity<>(sensorDataService.addSensorData(sensorDataDTO),HttpStatus.CREATED);
		}catch(DataIntegrityViolationException e){
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="Error de clave.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
	}

}
