package com.tld.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class SensorDataDTO {		
    private String sensorApiKey;
    private Long sensorDataCorrelative;
    private String sensorDataEntry;
    private Boolean sensorDataIsActive;
}
