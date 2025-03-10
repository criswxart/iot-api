package com.tld.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table (name="company")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="company_id")
	private Integer companyId;	
	
	@Column(name="company_name", nullable = false)
	private String companyName;	
	
	@Column(name="company_api_key", nullable = false)
	private String companyApiKey;	
	
	@ManyToOne
    @JoinColumn(name = "company_created_by", nullable = false)	
	private Users companyCreatedBy;
	
	@Column(name="company_created_at", nullable = false)
	private Long companyCreatedAt;
	
	@Column(name="company_active", nullable = false)
	private Boolean companyActive;
	
	@ManyToOne
	@JoinColumn(name="company_modified_by", nullable = false)
	private Users companyModifiedBy;
	
	@Column(name="company_modified_at", nullable = false)
	private Long companyModifiedAt;
	
	
	public Company(Integer companyId) {		
		this.companyId=companyId;		
	}

}


