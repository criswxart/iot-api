package com.tld.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tld.dto.CompanyDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
    private CompanyRepository repository;
    
    @Override
    public List<CompanyDTO> getAllCompanies() {
        return repository.findAll().stream()
                .map(CompanyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
