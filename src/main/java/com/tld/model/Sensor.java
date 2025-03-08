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
@Table (name="sensor")
public class Sensor {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sensor_id")
	private Integer sensorId;	
	
	@ManyToOne
    @JoinColumn(name = "location_id", nullable = false)	
	private Location locationId;	
	
	@Column(name="sensor_name", nullable = false)
	private String sensorName;	
	
	@ManyToOne
    @JoinColumn(name = "category_id", nullable = false)	
	private Category categoryId;
	
	@Column(name="sensor_meta", nullable = false)
	private String sensorMeta;
	
	@Column(name="sensor_api_key", nullable = false, unique = true )
	private String sensorApiKey;	
	
	@ManyToOne
	@JoinColumn(name="sensor_created_by", nullable = false)
	private Users sensorCreatedBy;
	
	@Column(name="sensor_created_at", nullable = false)
	private Long sensorCreatedAt;
	
	@Column(name="sensor_active", nullable = false)
	private Boolean sensorActive;
	
	@ManyToOne
	@JoinColumn(name="sensor_modified_by", nullable = false)
	private Users sensorModifiedBy;
	
	@Column(name="sensor_modified_at", nullable = false)
	private Long sensorModifiedAt;
	
	
	
}
