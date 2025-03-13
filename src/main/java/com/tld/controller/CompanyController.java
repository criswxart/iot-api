package com.tld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.CompanyDTO;
import com.tld.service.CompanyService;

@RestController
@RequestMapping("api/companies")
public class CompanyController {
	
	@Autowired
    private CompanyService service;
    
    @GetMapping
    public List<CompanyDTO> getAllCompanies() {
        return service.getAllCompanies();
    }
}
