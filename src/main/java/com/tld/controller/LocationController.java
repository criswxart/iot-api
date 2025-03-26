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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.LocationDTO;
import com.tld.service.LocationService;
import com.tld.util.LogUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {

	private final LocationService locationService;	
	@PostMapping
	public ResponseEntity <?> addLocation(@RequestBody LocationDTO locationDTO){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller locationDTO");
	    return ResponseEntity.ok(locationService.addLocation(locationDTO));			
	}
	
	
	//Parametro field puede ser; ciudad, pais, direccion, id, usuario
	//Parametro value puede ser; iquique, chile, calleX, 1, luis
	@GetMapping
	public ResponseEntity<?> getLocations(@RequestParam String field, @RequestParam String value){
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getLocations");
		return new  ResponseEntity<>(locationService.getLocations(field, value),HttpStatus.OK);				
	}
	
		
	
	@PutMapping("{locationId}")
	public ResponseEntity <?> updateLocation(@PathVariable Long locationId, @RequestBody LocationDTO locationDTO){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateLocation");
	    return ResponseEntity.ok(locationService.updateLocation(locationId, locationDTO));				
	}
	
	
	@DeleteMapping("{locationId}")
	public ResponseEntity <String> deleteLocation(@PathVariable Long locationId){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteLocation");
		return ResponseEntity.ok(locationService.deleteLocation(locationId));
	}
}
