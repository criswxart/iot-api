package com.tld.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name="location",  uniqueConstraints = @UniqueConstraint(columnNames = {"city_id", "location_adress"}))
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="location_id")
	private Integer locationId;	
	
	@ManyToOne
    @JoinColumn(name = "company_id", nullable = false)	
	private Company company;	
	
	@Column(name="location_adress", nullable = false)
	private String locationAdress;	
	
	@ManyToOne
    @JoinColumn(name = "city_id", nullable = false)	
	private City city;
	
	@Column(name="location_meta", nullable = false)
	private String locationMeta;
	
	@ManyToOne
	@JoinColumn(name="location_created_by", nullable = false)
	private Users locationCreatedBy;
	
	@Column(name="location_created_at", nullable = false)
	private Long locationCreatedAt;
	
	@Column(name="location_active", nullable = false)
	private Boolean locationActive;
	
	@ManyToOne
	@JoinColumn(name="location_modified_by", nullable = false)
	private Users locationModifiedBy;
	
	@Column(name="location_modified_at", nullable = false)
	private Long locationModifiedAt;
	
	
	
	public Location(Company company,String locationAdress, City city, String locationMeta, Users locationCreatedBy,Long locationCreatedAt, 
			Boolean locationActive, Users locationModifiedBy,Long locationModifiedAt) {		
		this.company=company;
		this.locationAdress=locationAdress;
		this.city=city;
		this.locationMeta=locationMeta;
		this.locationCreatedBy=locationCreatedBy;
		this.locationCreatedAt=locationCreatedAt;
		this.locationCreatedAt=locationCreatedAt;
		this.locationActive=locationActive;
		this.locationModifiedBy=locationModifiedBy;
		this.locationModifiedAt=locationModifiedAt;		
	}
	
	
	
	
}
