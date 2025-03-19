package com.tld.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.CompanyDTO;
import com.tld.dto.SensorDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.service.CompanyService;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/sensor")
@RequiredArgsConstructor
public class SensorController {
	
	private final SensorService sensorService;
	
	
	@PostMapping("add")
	public ResponseEntity <?> addSensor(@RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){
		sensorDTO.setSensorApiKey(companyApiKey);
		try {
			return new  ResponseEntity<>(sensorService.addSensor(sensorDTO),HttpStatus.CREATED);
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

}
