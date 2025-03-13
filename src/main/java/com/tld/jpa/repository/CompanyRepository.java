package com.tld.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tld.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
