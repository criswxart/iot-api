package com.tld.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tld.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	 Optional<Company> findByCompanyApiKey(String companyApiKey);

}
