package com.tld.controller;

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

import com.tld.dto.CompanyDTO;
import com.tld.dto.CompanyInfoDTO;
import com.tld.dto.ErrorDTO;
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
	    } catch (DataIntegrityViolationException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
	    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
	    }
	}	
	
	@PutMapping("{sensorId}")
	public ResponseEntity <?> updateCompany(@PathVariable Long sensorId, @RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){	
		sensorDTO.setSensorId(sensorId);
		try {			    		    			
			SensorInfoDTO updatedSensor = sensorService.updateSensor(companyApiKey,sensorDTO);
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
   	public ResponseEntity<?> getSensors(@RequestParam String field, @RequestParam String value,@RequestHeader("company_api_key") String companyApiKey){    
   		try {			
   			return new  ResponseEntity<>(sensorService.getSensors(field, value, companyApiKey),HttpStatus.OK);
   		} catch (DataIntegrityViolationException e) {
   		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
   	    } catch (Exception e) {
   		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
   		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
   		}				
   	}
       
    
    
    @DeleteMapping("{sensorId}")
	public ResponseEntity <String> deleteLocation(@PathVariable Long sensorId,@RequestHeader("company_api_key") String companyApiKey){		
		String mensaje= sensorService.deleteSensor(sensorId, companyApiKey);
		return ResponseEntity.ok(mensaje);
	}
	
	
}
