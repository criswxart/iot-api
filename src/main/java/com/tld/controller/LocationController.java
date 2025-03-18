package com.tld.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.model.Users;
import com.tld.service.LocationService;
import com.tld.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
public class LocationController {

	private final LocationService locationService;	
	private final UserService userService;	
	@PostMapping()
	public ResponseEntity <?> addLocation(@RequestBody LocationDTO locationDTO){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userName = authentication.getName(); 		    
	    Optional<Users> user = userService.findByUsername(userName);		    
	    
	    locationDTO.setLocationCreatedBy(user.get().getUserId());
	    locationDTO.setLocationModifiedBy(user.get().getUserId());	        

		try {
			return new  ResponseEntity<>(locationService.addLocation(locationDTO),HttpStatus.CREATED);
		}catch(DataIntegrityViolationException e){
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="No se puede ingresar la misma direccion mas de una vez en una ciudad.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
		
	}
	
	@GetMapping("country")
	public ResponseEntity<?> getLocationsByCountryName(@RequestParam String countryName){
		try {			
			return new  ResponseEntity<>(locationService.getLocationsByCountryName(countryName),HttpStatus.OK);
		}catch (DataIntegrityViolationException e) {  			
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="No se puede ingresar la misma direccion mas de una vez en una ciudad.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
					
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllLocations(){
		try {			
			return new  ResponseEntity<>(locationService.getAllLocations(),HttpStatus.OK);
		}catch (DataIntegrityViolationException e) {  			
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="No se puede ingresar la misma direccion mas de una vez en una ciudad.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
					
	}
	
	@GetMapping("id")
	public ResponseEntity<?> getLocationsById(@RequestParam Long locationId){
		try {			
			return new  ResponseEntity<>(locationService.getLocationById(locationId),HttpStatus.OK);
		}catch (DataIntegrityViolationException e) {  			
			String respuesta;
			if(e.getMessage().contains("not found")) {
				respuesta="No hay nada.";
			}else {
				respuesta="Error";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
					
	}
	
	
	@PutMapping("{locationId}")
	public ResponseEntity <?> updateLocation(@PathVariable Long locationId, @RequestBody LocationDTO locationDTO){			
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String userName = authentication.getName(); 		    
		    Optional<Users> user = userService.findByUsername(userName);		    
		    locationDTO.setLocationModifiedBy(user.get().getUserId());	    
		    		    			
			LocationInfoDTO updatedLocation = locationService.updateLocation(locationId, locationDTO);
		    return ResponseEntity.ok(updatedLocation);
		    
		}catch(DataIntegrityViolationException e){
			String respuesta;
			if(e.getMessage().contains("llave duplicada")) {
				respuesta="No se puede ingresar la misma direccion mas de una vez en una ciudad.";
			}else {
				respuesta="El json presenta errores de datos o formato";
			}			
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);			
		}
		
	}
	
	@DeleteMapping("{locationId}")
	public ResponseEntity <String> deleteLocation(@PathVariable Long locationId){		
		String mensaje= locationService.deleteLocation(locationId);
		return ResponseEntity.ok(mensaje);
	}
}
