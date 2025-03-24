package com.tld.model.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SensorDataId implements Serializable {

    @Column(name = "sensor_api_key")
    private String sensorApiKey;

    @Column(name = "sensor_data_correlative")
    private Long sensorDataCorrelative;

    public SensorDataId() {}

    public SensorDataId(String sensorApiKey, Long sensorCorrelative) {
        this.sensorApiKey = sensorApiKey;
        this.sensorDataCorrelative = sensorCorrelative;
    }

    public String getSensorApiKey() { return sensorApiKey; }
    public void setSensorApiKey(String sensorApiKey) { this.sensorApiKey = sensorApiKey; }

    public Long getSensorCorrelative() { return sensorDataCorrelative; }
    public void setSensorCorrelative(Long sensorCorrelative) { this.sensorDataCorrelative = sensorCorrelative; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDataId that = (SensorDataId) o;
        return Objects.equals(sensorApiKey, that.sensorApiKey) &&
               Objects.equals(sensorDataCorrelative, that.sensorDataCorrelative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorApiKey, sensorDataCorrelative);
    }
}
