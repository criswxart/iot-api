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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.ErrorDTO;
import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.service.LocationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {

	private final LocationService locationService;	
	@PostMapping
	public ResponseEntity <?> addLocation(@RequestBody LocationDTO locationDTO){
		try {    			
			LocationInfoDTO addedLocation = locationService.addLocation(locationDTO);
		    return ResponseEntity.ok(addedLocation);
		    
		} catch (DataIntegrityViolationException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorDTO("Error inesperado", e.getMessage()));
		}				
	}
	
	
	//Parametro field puede ser; ciudad, pais, direccion, id, usuario
	//Parametro value puede ser; iquique, chile, calleX, 1, luis
	@GetMapping
	public ResponseEntity<?> getLocations(@RequestParam String field, @RequestParam String value){
		try {			
			return new  ResponseEntity<>(locationService.getLocations(field, value),HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorDTO("Error inesperado", e.getMessage()));
		}				
	}
	
		
	
	@PutMapping("{locationId}")
	public ResponseEntity <?> updateLocation(@PathVariable Long locationId, @RequestBody LocationDTO locationDTO){			
		try {    			
			LocationInfoDTO updatedLocation = locationService.updateLocation(locationId, locationDTO);
		    return ResponseEntity.ok(updatedLocation);
		    
		} catch (DataIntegrityViolationException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorDTO("Error inesperado", e.getMessage()));
		}				
	}
	
	
	@DeleteMapping("{locationId}")
	public ResponseEntity <String> deleteLocation(@PathVariable Long locationId){		
		String mensaje= locationService.deleteLocation(locationId);
		return ResponseEntity.ok(mensaje);
	}
}
