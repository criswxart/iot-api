package com.tld.service.impl;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tld.dto.CompanyDTO;
import com.tld.dto.CompanyInfoDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.UserRepository;
import com.tld.mapper.CompanyMapper;
import com.tld.mapper.LocationMapper;
import com.tld.model.City;
import com.tld.model.Company;
import com.tld.model.Location;
import com.tld.model.Users;
import com.tld.service.CompanyService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{
		
    final CompanyRepository  companyRepository;
    final UserRepository userRepository;
    
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.of("America/Santiago"));
    
	@Override
	public CompanyInfoDTO addCompany(CompanyDTO companyDTO) {		
	    
	    Optional<Users> optionalUser =  getAuthenticatedUser();    
		
		Company company= CompanyMapper.toEntity(companyDTO);
		company.setCompanyCreatedBy(optionalUser.get());
		company.setCompanyModifiedBy(optionalUser.get());		
    	companyRepository.save(company).getCompanyId();

    	return new CompanyInfoDTO(	
				company.getCompanyId(),
				company.getCompanyName(),
				company.getCompanyApiKey(),
				company.getCompanyCreatedBy().getUserName(),
				formatter.format(company.getCompanyCreatedAt()),			
				company.getCompanyModifiedBy().getUserName(),
				formatter.format(company.getCompanyModifiedAt())
				
		    );	
	}
	
	@Override
	public List<CompanyInfoDTO> getCompanies(String field, String value) {		
		return companyRepository.findCompanies(field, value);
	}
	
	@Override
	public CompanyInfoDTO updateCompany(Long companyId, CompanyDTO companyDTO) {
		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if (optionalCompany.isEmpty()) {
	    	throw new EntityNotFoundException("Company no encontrada con ID: " + companyId );
	    }	
		Company company = optionalCompany.get();
		
		Optional<Users> optionalUser =  getAuthenticatedUser();    
	    company.setCompanyModifiedBy(optionalUser.get());
		
		
		if(companyDTO.getCompanyName()!=null) {
			company.setCompanyName(companyDTO.getCompanyName());
		}		
		if(companyDTO.getCompanyApiKey()!=null) {			
			company.setCompanyApiKey(companyDTO.getCompanyApiKey());			
		}	

		company.setCompanyIsActive(true);		
		companyRepository.save(company);
		
		return new CompanyInfoDTO(	
				company.getCompanyId(),
				company.getCompanyName(),
				company.getCompanyApiKey(),
				company.getCompanyCreatedBy().getUserName(),
				formatter.format(company.getCompanyCreatedAt()),
				company.getCompanyModifiedBy().getUserName(),
				formatter.format(company.getCompanyModifiedAt())
				
		    );	
	}	

	@Override
	public String deleteCompany(Long companyId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.of("America/Santiago"));		
		Optional <Company> optionalCompany=companyRepository.findById(companyId);		
		if (optionalCompany.isEmpty()) {
			throw new EntityNotFoundException("No existe ninguna location con id: " + companyId);
		}			
		Company company= optionalCompany.get();		
		
		if(!optionalCompany.get().getCompanyIsActive()) {
			return "El registro ya esta inactivo, fue hecho por "+company.getCompanyModifiedBy().getUserName()+
					" a las "+formatter.format(company.getCompanyModifiedAt());
		}		
		company.setCompanyIsActive(false);
		
		Optional<Users> optionalUser =  getAuthenticatedUser();     
	    company.setCompanyModifiedBy(optionalUser.get());
		
		companyRepository.save(company);
		return "Se inactiva compania de nombre: "+company.getCompanyName()+" por: "+company.getCompanyModifiedBy().getUserName() 
				+" a las "+ formatter.format(company.getCompanyModifiedAt());
	}		
	   
	@Override
	public Optional<CompanyDTO> getCompanyByApiKey(String apiKey) {			
		 return companyRepository.findByCompanyApiKey(apiKey).map(CompanyMapper::toDTO);
	}
	
	 private Optional<Users> getAuthenticatedUser() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication != null && authentication.isAuthenticated()) {
	            String userName = authentication.getName();
	            return userRepository.findByUserName(userName);
	        }
	        return Optional.empty();
	    }
	
	
	
}
