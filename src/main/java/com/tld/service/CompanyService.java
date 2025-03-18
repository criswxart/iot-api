package com.tld.service;

import java.util.List;
import java.util.Optional;

import com.tld.dto.CompanyDTO;
import com.tld.dto.CompanyInfoDTO;

public interface CompanyService {
	
	Optional<CompanyDTO> getCompanyByApiKey(String apiKey);
	
	List<CompanyInfoDTO>getCompanies(String field, String value);
}
