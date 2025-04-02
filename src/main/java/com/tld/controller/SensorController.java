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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.SensorDTO;
import com.tld.dto.info.SensorInfoDTO;
import com.tld.service.SensorService;
import com.tld.util.LogUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/sensor")
@RequiredArgsConstructor
@Tag(name = "Sensor Controller", description = "Gestión de Sensores")
public class SensorController {
	
	private final SensorService sensorService;
	
	
	@PostMapping("/sensor")
	@Operation(summary = "Agregar un nuevo sensor", description = "Este endpoint permite agregar un nuevo sensor a la base de datos. Realiza validaciones sobre la existencia de la compañía, la categoría y la dirección antes de agregar el sensor.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Sensor agregado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorInfoDTO.class))),
	    @ApiResponse(responseCode = "400", description = "La API key proporcionada no corresponde a una compañía válida"),
	    @ApiResponse(responseCode = "404", description = "La dirección o categoría no existe"),
	    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity <?> addSensor(@RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){
		LogUtil.log(SensorController.class, Level.INFO, "Solicitud recibida en controller addSensor");
		sensorDTO.setSensorApiKey(companyApiKey);
		return ResponseEntity.ok(sensorService.addSensor(sensorDTO));
	}	
	
	@PutMapping("{sensorId}")
	@Operation(summary = "Actualizar un sensor", description = "Este endpoint permite actualizar un sensor existente. Valida la existencia del sensor, la compañía y la categoría antes de realizar el cambio.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Sensor actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorInfoDTO.class))),
	    @ApiResponse(responseCode = "400", description = "La API key proporcionada no corresponde a una compañía válida o la categoría no existe"),
	    @ApiResponse(responseCode = "404", description = "Sensor no encontrado"),
	    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity <?> updateSensor(@PathVariable Long sensorId, @RequestBody SensorDTO sensorDTO, @RequestHeader("company_api_key") String companyApiKey){	
		LogUtil.log(SensorController.class, Level.INFO, "Solicitud recibida en controller updateSensor");
		sensorDTO.setSensorId(sensorId);	    		    			
	    return ResponseEntity.ok(sensorService.updateSensor(companyApiKey,sensorDTO));		
	}	
	
	
	@GetMapping
	@Operation(summary = "Obtener sensores", description = "Este endpoint permite obtener una lista de sensores filtrados por un campo y valor. La API key de la compañía es necesaria para la validación.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Lista de sensores obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorInfoDTO.class))),
	    @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos"),
	    @ApiResponse(responseCode = "404", description = "No se encontraron sensores para los criterios proporcionados"),
	    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
   	public ResponseEntity<?> getSensors(@RequestParam String field, @RequestParam String value,@RequestHeader("company_api_key") String companyApiKey){  		
		LogUtil.log(SensorController.class, Level.INFO, "Solicitud recibida en controller getSensors");
		return new  ResponseEntity<>(sensorService.getSensors(field, value, companyApiKey),HttpStatus.OK);			
   	}
       
    
    
    @DeleteMapping("{sensorId}")
    @Operation(summary = "Eliminar un sensor", description = "Este endpoint permite inactivar un sensor en lugar de eliminarlo físicamente. Se valida que la API key corresponda a la compañía que posee el sensor.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sensor inactivado correctamente"),
        @ApiResponse(responseCode = "400", description = "La API key proporcionada no corresponde a la compañía del sensor"),
        @ApiResponse(responseCode = "404", description = "Sensor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
	public ResponseEntity <String> deleteSensor(@PathVariable Long sensorId,@RequestHeader("company_api_key") String companyApiKey){		
    	LogUtil.log(SensorController.class, Level.INFO, "Solicitud recibida en controller deleteSensor");
    	String mensaje= sensorService.deleteSensor(sensorId, companyApiKey);
		return ResponseEntity.ok(mensaje);
	}
	
	
}
