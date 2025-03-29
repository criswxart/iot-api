package com.tld.controller;

import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tld.dto.MeasurementDTO;
import com.tld.service.MeasurementService;
import com.tld.util.LogUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/measurement")
@RequiredArgsConstructor
public class MeasurementController {
	
	private final MeasurementService measurementService;								
	
	@PostMapping
	public ResponseEntity <?> addSensorData(@RequestBody MeasurementDTO measurementDTO, @RequestHeader("sensor_api_key") String sensorApiKey){
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller addSensorData");
		  LogUtil.log(CompanyController.class, Level.INFO, "JSON recibido: " + measurementDTO);
		measurementDTO.setApi_key(sensorApiKey);
		return ResponseEntity.ok(measurementService.addSensorData(measurementDTO, sensorApiKey));

	}	
	
	@PutMapping
	public ResponseEntity <?> updateSensorData(@RequestParam Long id, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){		
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateSensorData");
		return ResponseEntity.ok(measurementService.updateSensorData(sensorApiKey, id, companyApiKey));
		
	}	
	
	
	@GetMapping
   	public ResponseEntity<?> getSensorById(@RequestParam Long measurementID, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){    
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensorById");
		return new  ResponseEntity<>(measurementService.getSensorDataById(measurementID,sensorApiKey,companyApiKey),HttpStatus.OK);		
   	}
	
	@GetMapping("epoch")
   	public ResponseEntity<?> getSensorByEpoch(@RequestParam Long from, @RequestParam Long to, @RequestHeader("company_api_key") String companyApiKey){    
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensorByEpoch");
		return new  ResponseEntity<>(measurementService.getSensorDataByEpoch(from, to, companyApiKey),HttpStatus.OK);			
   	}
	
	@GetMapping("company")
   	public ResponseEntity<?> getSensorByCompany(@RequestHeader("company_api_key") String companyApiKey){ 
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getSensorByCompany");
		return new  ResponseEntity<>(measurementService.getSensorDataByCompany(companyApiKey),HttpStatus.OK);			
   	}      
    
    
    @DeleteMapping
	public ResponseEntity <?> deleteSensorData(@RequestParam Long id, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteSensorData");
    	return new  ResponseEntity<>(measurementService.deleteSensorData(sensorApiKey,id,companyApiKey),HttpStatus.OK);
	}
}
