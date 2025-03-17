package com.tld.mapper;

import org.springframework.stereotype.Component;

import com.tld.dto.CompanyDTO;
import com.tld.model.Company;

@Component
public class CompanyMapper {
	public static CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company.getCompanyId(), company.getCompanyName(), company.getCompanyApiKey());
    }
	
	
	public static Company toEntity (CompanyDTO companyDTO) {
        return new Company(companyDTO.getCompanyName(), companyDTO.getCompanyApiKey());
    }
}
