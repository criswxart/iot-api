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

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/location")
@RequiredArgsConstructor
@Tag(name = "Location Controller", description = "Gestión de ubicaciones")
public class LocationController {

	private final LocationService locationService;
	
	 @Operation(summary = "Agregar una nueva ubicación", description = "Recibe un objeto LocationDTO y lo guarda en la base de datos")
	    @PostMapping
	public ResponseEntity <?> addLocation(@RequestBody LocationDTO locationDTO){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller locationDTO");
	    return ResponseEntity.ok(locationService.addLocation(locationDTO));			
	}
	
	
	//Parametro field puede ser; ciudad, pais, direccion, id, usuario
	//Parametro value puede ser; iquique, chile, calleX, 1, luis
	  @Operation(summary = "Obtener ubicaciones", description = "Busca ubicaciones por un campo y un valor específicos")
	    @GetMapping
	public ResponseEntity<?> getLocations(
			 @Parameter(description = "Campo por el cual se filtrará la búsqueda, por ejemplo: ciudad, país, dirección, id, usuario")
			@RequestParam String field,
			@Parameter(description = "Valor correspondiente al campo de búsqueda")
			@RequestParam String value){
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getLocations");
		return new  ResponseEntity<>(locationService.getLocations(field, value),HttpStatus.OK);				
	}
	
		
	
	  @Operation(summary = "Actualizar una ubicación", description = "Actualiza los datos de una ubicación dada su ID")
	    @PutMapping("{locationId}")
	public ResponseEntity <?> updateLocation(  @Parameter(description = "ID de la ubicación a actualizar") @PathVariable Long locationId, @RequestBody LocationDTO locationDTO){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateLocation");
	    return ResponseEntity.ok(locationService.updateLocation(locationId, locationDTO));				
	}
	
	
	  @Operation(summary = "Eliminar una ubicación", description = "Desactiva una ubicación sin eliminarla físicamente de la base de datos")
	    @DeleteMapping("{locationId}")
	public ResponseEntity <String> deleteLocation(
			 @Parameter(description = "ID de la ubicación a eliminar")
			 @PathVariable Long locationId){	
		LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteLocation");
		return ResponseEntity.ok(locationService.deleteLocation(locationId));
	}
}
