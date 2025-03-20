package com.tld.controller;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.SensorDTO;
import com.tld.dto.SensorDataDTO;
import com.tld.exception.DuplicateKeyException;
import com.tld.exception.InvalidApiKeyException;
import com.tld.exception.InvalidJsonFormatException;
import com.tld.service.SensorDataService;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/sensordata")
@RequiredArgsConstructor
public class SensorDataController {

	private final SensorDataService sensorDataService;

	private final SensorService sensorService;

	@PostMapping("add")
	public ResponseEntity<?> addSensorData(@RequestBody SensorDataDTO sensorDataDTO,
			@RequestHeader("sensor_api_key") String sensorApiKey) {

		sensorDataDTO.setSensorApiKey(sensorApiKey);
		sensorDataDTO.setSensorCorrelative(null);
		Optional<SensorDTO> sensorDTO = sensorService.getSensorByApiKey(sensorApiKey);
		System.out.println("apiKey " + sensorApiKey + " SensorDTO" + sensorDataDTO.getSensorEntry());
		if (sensorDTO.isEmpty()) {
			throw new InvalidApiKeyException("No tienes una llave válida.");
		}

		try {
			return new ResponseEntity<>(sensorDataService.addSensorData(sensorDataDTO), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("llave duplicada")) {
				throw new DuplicateKeyException("Sensor", "sensor api key");
			} else {
				throw new InvalidJsonFormatException("El JSON presenta errores de datos o formato.");
			}
		}
	}

}
