package com.tld.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.LocationDTO;
import com.tld.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/location")
@RequiredArgsConstructor
public class LocationController {

	private final LocationService locationService;

	@PostMapping("add")
	public ResponseEntity <?> addLocation(@RequestBody LocationDTO locationDTO){
		System.out.println(locationDTO);
		
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
	
	@GetMapping("by-country")
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
	
	
}
