package com.tld.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tld.dto.CompanyDTO;
import com.tld.dto.CompanyInfoDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{
		
    final CompanyRepository  companyRepository;	
    
	@Override
	public Optional<CompanyDTO> getCompanyByApiKey(String apiKey) {			
		 return companyRepository.findByCompanyApiKey(apiKey).map(CompanyMapper::toDTO);
	}
	
	@Override
	public List<CompanyInfoDTO> getCompanies(String field, String value) {
		System.out.println("valores en IMPL "+field+"   "
				+ ""+value );
		
		return companyRepository.findCompanies(field, value);
	}	
   
}
