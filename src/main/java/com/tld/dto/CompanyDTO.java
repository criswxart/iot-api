package com.tld.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
	
	private Long companyId;
    private String companyName;
    private String companyApiKey;
}
