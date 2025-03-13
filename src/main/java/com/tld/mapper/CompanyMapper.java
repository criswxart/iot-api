package com.tld.mapper;

import com.tld.dto.CompanyDTO;
import com.tld.model.Company;

public class CompanyMapper {
	public static CompanyDTO toDTO(Company company) {
        return new CompanyDTO(company.getCompanyId(), company.getCompanyName(), company.getCompanyApiKey());
    }
}
