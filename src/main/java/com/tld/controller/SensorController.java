package com.tld.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.CompanyDTO;
import com.tld.dto.CompanyInfoDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.SensorInfoDTO;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/sensor")
@RequiredArgsConstructor
public class SensorController {
	
	private final SensorService sensorService;
	
	
	@PostMapping
	public ResponseEntity <?> addSensor(@RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){
		sensorDTO.setSensorApiKey(companyApiKey);
		try {
			SensorInfoDTO addedSensor=sensorService.addSensor(sensorDTO);		
			
			return ResponseEntity.ok(addedSensor);
		}catch(DataIntegrityViolationException e){
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="No puedes repetir el campo sensor_api_key.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
	}
	
	@PutMapping("{sensorId}")
	public ResponseEntity <?> updateCompany(@PathVariable Long sensorId, @RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){		
		sensorDTO.setSensorApiKey(companyApiKey);
		sensorDTO.setSensorId(sensorId);
		try {			    		    			
			SensorInfoDTO updatedSensor = sensorService.updateSensor(sensorDTO);
		    return ResponseEntity.ok(updatedSensor);
		    
		}catch(DataIntegrityViolationException e){
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="No se puede ingresar dos veces la misma compania o repetir la company api key.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
		
	}
}
