package com.tld.controller;

import java.util.Optional;
import java.util.logging.Level;

import org.springframework.dao.DataIntegrityViolationException;
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

import com.tld.dto.ErrorDTO;
import com.tld.dto.SensorDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.dto.info.SensorDataInfoDTO;
import com.tld.service.SensorDataService;
import com.tld.service.SensorService;
import com.tld.util.LogUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/sensordata")
@RequiredArgsConstructor
public class SensorDataController {
	
	private final SensorDataService sensorDataService;								
	
	@PostMapping
	public ResponseEntity <?> addSensorData(@RequestBody SensorDataDTO sensorDataDTO, @RequestHeader("sensor_api_key") String sensorApiKey){
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller addSensorData");
	//	sensorDataDTO.setSensorApiKey(sensorApiKey);
		return ResponseEntity.ok(sensorDataService.addSensorData(sensorDataDTO));

	}	
	
	@PutMapping
	public ResponseEntity <?> updateSensorData(@RequestBody SensorDataDTO sensorDataDTO, @RequestHeader("company_api_key") String companyApiKey){		
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateSensorData");
		return ResponseEntity.ok(sensorDataService.updateSensorData(sensorDataDTO,companyApiKey));
		
	}	
	
	
	@GetMapping
   	public ResponseEntity<?> getSensorById(@RequestParam Long sensorDataCorrel, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){    
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensorById");
		return new  ResponseEntity<>(sensorDataService.getSensorDataById(sensorDataCorrel, sensorApiKey, companyApiKey),HttpStatus.OK);		
   	}
	
	@GetMapping("epoch")
   	public ResponseEntity<?> getSensorByEpoch(@RequestParam Long from, @RequestParam Long to, @RequestHeader("company_api_key") String companyApiKey){    
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensorByEpoch");
		return new  ResponseEntity<>(sensorDataService.getSensorDataByEpoch(from, to, companyApiKey),HttpStatus.OK);			
   	}
	
	@GetMapping("company")
   	public ResponseEntity<?> getSensorByCompany(@RequestHeader("company_api_key") String companyApiKey){ 
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensorByCompany");
		return new  ResponseEntity<>(sensorDataService.getSensorDataByCompany(companyApiKey),HttpStatus.OK);			
   	}
       
    
    
    @DeleteMapping
	public ResponseEntity <?> deleteSensorData(@RequestParam Long sensorDataCorrel, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteSensorData");
    	return new  ResponseEntity<>(sensorDataService.deleteSensorData(sensorApiKey,sensorDataCorrel,companyApiKey),HttpStatus.OK);
	}
	

}
