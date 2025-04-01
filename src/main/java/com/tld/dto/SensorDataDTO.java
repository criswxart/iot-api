package com.tld.dto;


import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tld.dto.deserializer.SensorDataDeserializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class SensorDataDTO {		
    private String sensorApiKey;
    private Long sensorDataCorrelative;
    private String sensorDataEntry;
    private Boolean sensorDataIsActive;
}
