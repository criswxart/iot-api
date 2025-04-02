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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/measurement")
@RequiredArgsConstructor
@Tag(name = "Measurement Controller", description = "Controlador para la gestión de mediciones de sensores")
public class MeasurementController {
	
	private final MeasurementService measurementService;								
	
	@PostMapping
    @Operation(summary = "Agregar datos de sensor", description = "Recibe datos de medición y los almacena en el sistema.")
	public ResponseEntity <?> addSensorData(@RequestBody MeasurementDTO measurementDTO, @RequestHeader(value ="sensor_api_key", required = false)
	 @Parameter(description = "Clave API del sensor") String sensorApiKey){
		LogUtil.log(MeasurementController.class, Level.INFO, "Solicitud recibida en controller addSensorData");	
		//measurementDTO.setApi_key(sensorApiKey);
		return ResponseEntity.ok(measurementService.addSensorData(measurementDTO, sensorApiKey));

	}	
	
	@PutMapping
    @Operation(summary = "Actualizar datos de sensor", description = "Actualiza la información de una medición específica.")
	public ResponseEntity <?> updateSensorData(@RequestParam @Parameter(description = "ID de la medición a actualizar") Long id,
			@RequestParam @Parameter(description = "Clave API del sensor") String  sensorApiKey, 
			@RequestHeader("company_api_key") @Parameter(description = "Clave API de la compañía") String companyApiKey){		
		LogUtil.log(MeasurementController.class, Level.INFO, "Solicitud recibida en controller updateSensorData");
		return ResponseEntity.ok(measurementService.updateSensorData(sensorApiKey, id, companyApiKey));
		
	}	
	
	
	@GetMapping
    @Operation(summary = "Obtener datos de sensor por ID", description = "Recupera información de una medición utilizando su ID.")
   	public ResponseEntity<?> getSensorById(@RequestParam  @Parameter(description = "ID de la medición a obtener") Long measurementID, 
   			@RequestParam @Parameter(description = "Clave API del sensor") String sensorApiKey,
   			@RequestHeader("company_api_key")  @Parameter(description = "Clave API de la compañía") String companyApiKey){    
		LogUtil.log(MeasurementController.class, Level.INFO, "Solicitud recibida en controller getSensorById");
		return new  ResponseEntity<>(measurementService.getSensorDataById(measurementID,sensorApiKey,companyApiKey),HttpStatus.OK);		
   	}
	
	@GetMapping("epoch")
    @Operation(summary = "Obtener datos de sensor por rango de tiempo", description = "Recupera mediciones dentro de un rango de tiempo dado.")
   	public ResponseEntity<?> getSensorByEpoch(@RequestParam @Parameter(description = "Tiempo de inicio en formato epoch") Long from, 
   			@RequestParam @Parameter(description = "Tiempo de fin en formato epoch") Long to, 
   			@RequestHeader("company_api_key")  @Parameter(description = "Clave API de la compañía") String companyApiKey){    
		LogUtil.log(MeasurementController.class, Level.INFO, "Solicitud recibida en controller getSensorByEpoch");
		return new  ResponseEntity<>(measurementService.getSensorDataByEpoch(from, to, companyApiKey),HttpStatus.OK);			
   	}
	
	@GetMapping("company")
    @Operation(summary = "Obtener datos de sensor por compañía", description = "Recupera todas las mediciones asociadas a una compañía.")
   	public ResponseEntity<?> getSensorByCompany(@RequestHeader("company_api_key") @Parameter(description = "Clave API de la compañía") String companyApiKey){ 
		LogUtil.log(MeasurementController.class, Level.INFO, "Solicitud recibida en controller getSensorByCompany");
		return new  ResponseEntity<>(measurementService.getSensorDataByCompany(companyApiKey),HttpStatus.OK);			
   	}      
    
    
	@DeleteMapping
    @Operation(summary = "Eliminar datos de sensor", description = "Elimina una medición específica por su ID.")
	public ResponseEntity <?> deleteSensorData(@RequestParam @Parameter(description = "ID de la medición a eliminar") Long id, 
			@RequestParam  @Parameter(description = "Clave API del sensor") String sensorApiKey, 
			@RequestHeader("company_api_key")  @Parameter(description = "Clave API de la compañía") String companyApiKey){
    	LogUtil.log(MeasurementController.class, Level.INFO, "Solicitud recibida en controller deleteSensorData");
    	return new  ResponseEntity<>(measurementService.deleteSensorData(sensorApiKey,id,companyApiKey),HttpStatus.OK);
	}
}
