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

import com.tld.dto.CompanyDTO;
import com.tld.dto.CompanyInfoDTO;
import com.tld.dto.ErrorDTO;
import com.tld.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
	
    private final CompanyService companyService;
    
    @PostMapping
	public ResponseEntity <?> addCompany(@RequestBody CompanyDTO companyDTO){	
		try {			
			CompanyInfoDTO addedCompany = companyService.addCompany(companyDTO);
		    return ResponseEntity.ok(addedCompany);
		    
		   } catch (DataIntegrityViolationException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
		    }		
	}
    
    
   
    @PutMapping("{companyId}")
	public ResponseEntity <?> updateCompany(@PathVariable Long companyId, @RequestBody CompanyDTO companyDTO){			
		try {			    		    			
			CompanyInfoDTO updatedLocation = companyService.updateCompany(companyId, companyDTO);
		    return ResponseEntity.ok(updatedLocation);
		    
		} catch (DataIntegrityViolationException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
		} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorDTO("Error inesperado", e.getMessage()));
		}	
	}
    
    
    @GetMapping
   	public ResponseEntity<?> getCompanies(@RequestParam String field, @RequestParam String value){    
   		try {			
   			return new  ResponseEntity<>(companyService.getCompanies(field, value),HttpStatus.OK);
   		} catch (DataIntegrityViolationException e) {
   		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   		                .body(new ErrorDTO("Error de integridad de datos", e.getMessage()));
   	    } catch (Exception e) {
   		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
   		                .body(new ErrorDTO("Error inesperado", e.getMessage()));
   		}				
   	}
       
    
    
    @DeleteMapping("{companyId}")
	public ResponseEntity <String> deleteLocation(@PathVariable Long companyId){		
		String mensaje= companyService.deleteCompany(companyId);
		return ResponseEntity.ok(mensaje);
	}
	
		
    
}
