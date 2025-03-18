package com.tld.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyInfoDTO {
	
    private Long companyId;
    private String companyName;
    private String companyApiKey;
    private String userNameC;
    private java.sql.Timestamp companyCreatedAt;
    private String userNameM;
    private java.sql.Timestamp companyModifiedAt;


}
