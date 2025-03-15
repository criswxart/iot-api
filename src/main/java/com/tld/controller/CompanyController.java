package com.tld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.CompanyDTO;
import com.tld.service.CompanyService;
import com.tld.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
public class CompanyController {
	
    private CompanyService companyService;
    
    @GetMapping
    public List<CompanyDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }
    
}
