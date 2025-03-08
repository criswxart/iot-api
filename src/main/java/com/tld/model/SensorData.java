package com.tld.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sensor_data")
public class SensorData {
	
	@Id   
	@Column(name = "sensor_api_key")  
    private String sensorApiKey;
	
	@Id
    @Column(name = "sensor_correlative") 
    private Long sensorCorrelative;
    
    @ManyToOne
    @JoinColumn(name = "sensor_api_key", referencedColumnName = "sensor_api_key", insertable = false, updatable = false)
    private Sensor sensor;
    
	@Column(name="sensor_entry", nullable = false)
	private String sensorEntry;		

	@Column(name="sensor_data_created_at", nullable = false)
	private Long sensorDataCreatedAt;
}

