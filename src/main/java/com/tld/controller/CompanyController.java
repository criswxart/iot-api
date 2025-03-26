package com.tld.controller;




import java.util.logging.Level;

import com.tld.util.LogUtil;
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
public class CompanyController {
	
    private final CompanyService companyService;
    

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyDTO companyDTO) {	
        LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller addCompany");
        return ResponseEntity.ok(companyService.addCompany(companyDTO));
    }

    
   
    @PutMapping("{companyId}")
	public ResponseEntity <?> updateCompany(@PathVariable Long companyId, @RequestBody CompanyDTO companyDTO){
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller updateCompany");		    		    			
		//CompanyInfoDTO updatedLocation = companyService.updateCompany(companyId, companyDTO);
	    return ResponseEntity.ok(companyService.updateCompany(companyId, companyDTO));
	
	}
    
    
    @GetMapping
   	public ResponseEntity<?> getCompanies(@RequestParam String field, @RequestParam String value){   
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller getCompanies");		
		return new  ResponseEntity<>(companyService.getCompanies(field, value),HttpStatus.OK);
				
   	}
       
    
    
    @DeleteMapping("{companyId}")
	public ResponseEntity <?> deleteCompany(@PathVariable Long companyId){	
    	LogUtil.log(CompanyController.class, Level.INFO, "Solicitud recibida en controller deleteCompany");
    	return ResponseEntity.ok(companyService.deleteCompany(companyId).toString());
	
	}
	
		
    
}
