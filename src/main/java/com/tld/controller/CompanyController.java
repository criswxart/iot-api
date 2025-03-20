package com.tld.controller;




import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.exception.DuplicateKeyException;
import com.tld.exception.InvalidJsonFormatException;
import com.tld.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
	
    private final CompanyService companyService;  
    
    
    @GetMapping
    public ResponseEntity<?> getCompanies(@RequestParam String field, @RequestParam String value) {
        try {            
            return ResponseEntity.ok(companyService.getCompanies(field, value));
        } catch (DataIntegrityViolationException e) {  
            if (e.getMessage().contains("llave duplicada")) {
                throw new DuplicateKeyException("Compañía", field);
            } else {
                throw new InvalidJsonFormatException("El JSON presenta errores de datos o formato.");
            }
        }
    }

		
    
}
