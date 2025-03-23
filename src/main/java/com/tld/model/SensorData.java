package com.tld.model;
import java.time.Instant;

import com.tld.model.id.SensorDataId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensor_data")
public class SensorData {
	
	@EmbeddedId
    private SensorDataId sensorDataId; 	
    
    @ManyToOne
    @JoinColumn(name = "sensor_api_key", referencedColumnName = "sensor_api_key", insertable = false, updatable = false)
    private Sensor sensor;
    
	@Column(name="sensor_entry", nullable = false)
	private String sensorEntry;		

	@Column(name="sensor_data_created_at", nullable = false)
	private Instant sensorDataCreatedAt;
	
	@Column(name="sensor_data_modified_at", nullable = false)
	private Instant sensorDataModifiedAt;
	
	@Column(name="sensor_data_is_active", nullable = false)
	private Boolean sensorDataIsActive;
	
	@PrePersist
    protected void onCreate() {
        this.sensorDataCreatedAt = Instant.now(); // Se asigna al crear
        this.sensorDataModifiedAt = Instant.now();
        this.sensorDataIsActive= true;
	}
	
	@PreUpdate
	protected void onUpdate() {
        this.sensorDataModifiedAt = Instant.now(); // Solo se actualiza en update
    }   
		
    public SensorData(String sensorApiKey, Long sensorDataCorrelative, String sensorEntry) {
        this.sensorDataId = new SensorDataId(sensorApiKey, sensorDataCorrelative);
        this.sensorEntry = sensorEntry;
    }

}

