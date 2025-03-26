package com.tld.controller;

import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.SensorDTO;
import com.tld.service.SensorService;
import com.tld.util.LogUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/sensor")
@RequiredArgsConstructor
public class SensorController {
	
	private final SensorService sensorService;
	
	
	@PostMapping
	public ResponseEntity <?> addSensor(@RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller addSensor");
		sensorDTO.setSensorApiKey(companyApiKey);
		return ResponseEntity.ok(sensorService.addSensor(sensorDTO));
	}	
	
	@PutMapping("{sensorId}")
	public ResponseEntity <?> updateSensor(@PathVariable Long sensorId, @RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateSensor");
		sensorDTO.setSensorId(sensorId);	    		    			
	    return ResponseEntity.ok(sensorService.updateSensor(companyApiKey,sensorDTO));		
	}	
	
	
	@GetMapping
   	public ResponseEntity<?> getSensors(@RequestParam String field, @RequestParam String value,@RequestHeader("company_api_key") String companyApiKey){  		
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensors");
		return new  ResponseEntity<>(sensorService.getSensors(field, value, companyApiKey),HttpStatus.OK);			
   	}
       
    
    
    @DeleteMapping("{sensorId}")
	public ResponseEntity <String> deleteSensor(@PathVariable Long sensorId,@RequestHeader("company_api_key") String companyApiKey){		
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteSensor");
    	String mensaje= sensorService.deleteSensor(sensorId, companyApiKey);
		return ResponseEntity.ok(mensaje);
	}
	
	
}
