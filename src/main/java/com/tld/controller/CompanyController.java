package com.tld.controller;




import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tld.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
	
    private final CompanyService companyService;  
    
    
    @GetMapping
	public ResponseEntity<?> getCompanies(@RequestParam String field, @RequestParam String value){
    	
    	System.out.println("valores en controller "+field+"   "
				+ ""+value );
		try {			
			return new  ResponseEntity<>(companyService.getCompanies(field, value),HttpStatus.OK);
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
