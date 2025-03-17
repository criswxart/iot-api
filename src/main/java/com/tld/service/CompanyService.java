package com.tld.service;

import java.util.List;
import java.util.Optional;

import com.tld.dto.CompanyDTO;

public interface CompanyService {
	
	List<CompanyDTO> getAllCompanies();	
	Optional<CompanyDTO> getCompanyByApiKey(String apiKey);
}
