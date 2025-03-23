package com.tld.controller;

import java.util.Optional;

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
import com.tld.dto.SensorDataInfoDTO;
import com.tld.service.SensorDataService;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/sensordata")
@RequiredArgsConstructor
public class SensorDataController {
	
	private final SensorDataService sensorDataService;								
	
	@PostMapping
	public ResponseEntity <?> addSensorData(@RequestBody SensorDataDTO sensorDataDTO, @RequestHeader("sensor_api_key") String sensorApiKey){
		sensorDataDTO.setSensorApiKey(sensorApiKey);
		System.out.println("******************"+sensorApiKey);
		try {
			String addedSensorData=sensorDataService.addSensorData(sensorDataDTO);				
			return ResponseEntity.ok(addedSensorData);
	    } catch (DataIntegrityViolationException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
	    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
	    }
	}	
	
	@PutMapping
	public ResponseEntity <?> updateSensorData(@RequestBody SensorDataDTO sensorDataDTO, @RequestHeader("company_api_key") String companyApiKey){		
		try {			    		    			
			SensorDataInfoDTO updatedSensor = sensorDataService.updateSensorData(sensorDataDTO,companyApiKey);
		    return ResponseEntity.ok(updatedSensor);
		    
	    } catch (DataIntegrityViolationException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorDTO("Error inesperado", e.getMessage()));
    }
	}	
	
	
	@GetMapping
   	public ResponseEntity<?> getSensorById(@RequestParam Long sensorDataCorrel, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){    
   		try {			
   			return new  ResponseEntity<>(sensorDataService.getSensorDataById(sensorDataCorrel, sensorApiKey, companyApiKey),HttpStatus.OK);
   		} catch (DataIntegrityViolationException e) {
   		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
   	    } catch (Exception e) {
   		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
   		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
   		}				
   	}
	
	@GetMapping("epoch")
   	public ResponseEntity<?> getSensorByEpoch(@RequestParam Long from, @RequestParam Long to, @RequestHeader("company_api_key") String companyApiKey){    
   		try {			
   			return new  ResponseEntity<>(sensorDataService.getSensorDataByEpoch(from, to, companyApiKey),HttpStatus.OK);
   		} catch (DataIntegrityViolationException e) {
   		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
   	    } catch (Exception e) {
   		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
   		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
   		}				
   	}
	
	@GetMapping("company")
   	public ResponseEntity<?> getSensorByCompany(@RequestHeader("company_api_key") String companyApiKey){    
   		try {			
   			return new  ResponseEntity<>(sensorDataService.getSensorDataByCompany(companyApiKey),HttpStatus.OK);
   		} catch (DataIntegrityViolationException e) {
   		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
   	    } catch (Exception e) {
   		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
   		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
   		}				
   	}
       
    
    
    @DeleteMapping
	public ResponseEntity <?> deleteSensorData(@RequestParam Long sensorDataCorrel, @RequestParam String sensorApiKey, @RequestHeader("company_api_key") String companyApiKey){
		try {			
   			return new  ResponseEntity<>(sensorDataService.deleteSensorData(sensorApiKey,sensorDataCorrel,companyApiKey),HttpStatus.OK);
   		} catch (DataIntegrityViolationException e) {
   		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
   	    } catch (Exception e) {
   		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
   		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
   		}	
	}
	

}
