package com.tld.controller;




import java.util.logging.Level;

import com.tld.util.LogUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


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

import com.tld.dto.CompanyDTO;
import com.tld.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/company")
@RequiredArgsConstructor
@Tag(name = "Company Controller", description = "Gestion de Compañias")
public class CompanyController {
	
    private final CompanyService companyService;
    

    @Operation(summary = "Añadir una nueva empresa", description = "Este endpoint permite registrar una nueva empresa en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa añadida exitosamente",
                     content = @Content(schema = @Schema(implementation = CompanyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyDTO companyDTO) {	
        LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller addCompany");
        return ResponseEntity.ok(companyService.addCompany(companyDTO));
    }

    
   
    @Operation(summary = "Actualizar una empresa", description = "Este endpoint permite actualizar la información de una empresa existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa actualizada exitosamente",
                     content = @Content(schema = @Schema(implementation = CompanyDTO.class))),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @PutMapping("{companyId}")
	public ResponseEntity <?> updateCompany(@PathVariable Long companyId, @RequestBody CompanyDTO companyDTO){
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateCompany");		    		    			
		//CompanyInfoDTO updatedLocation = companyService.updateCompany(companyId, companyDTO);
	    return ResponseEntity.ok(companyService.updateCompany(companyId, companyDTO));
	
	}
    
    
    @Operation(summary = "Obtener empresas", description = "Este endpoint permite obtener empresas filtradas por un campo y valor específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida exitosamente")
    })
    @GetMapping
   	public ResponseEntity<?> getCompanies(@RequestParam String field, @RequestParam String value){   
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getCompanies");		
		return new  ResponseEntity<>(companyService.getCompanies(field, value),HttpStatus.OK);
				
   	}
       
    
    
    @Operation(summary = "Eliminar una empresa", description = "Este endpoint permite eliminar (desactivar) una empresa mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
    })
    @DeleteMapping("{companyId}")
	public ResponseEntity <?> deleteCompany(@PathVariable Long companyId){	
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteCompany");
    	return ResponseEntity.ok(companyService.deleteCompany(companyId).toString());
	
	}
	
		
    
}
