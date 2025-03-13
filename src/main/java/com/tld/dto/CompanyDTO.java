package com.tld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
	
	private Integer id;
    private String companyName;
    private String companyApiKey;
}
