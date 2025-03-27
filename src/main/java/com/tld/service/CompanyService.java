package com.tld.service;

import java.util.List;
import java.util.Optional;

import com.tld.dto.CompanyDTO;
import com.tld.dto.LocationDTO;
import com.tld.dto.info.CompanyInfoDTO;
import com.tld.dto.info.LocationInfoDTO;

public interface CompanyService {
	
	CompanyInfoDTO addCompany(CompanyDTO companyDTO);	
	CompanyInfoDTO updateCompany(Long companyId, CompanyDTO companyDTO) ;
	List<CompanyInfoDTO>getCompanies(String field, String value);
	String deleteCompany(Long companyId);
	
	
	//Se ocupa para validar entradas en entidad SENSOR
	Optional<CompanyDTO> getCompanyByApiKey(String apiKey);
	
	
}
