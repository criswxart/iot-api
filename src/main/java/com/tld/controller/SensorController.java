package com.tld.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tld.dto.CompanyDTO;
import com.tld.dto.SensorDTO;
import com.tld.exception.DuplicateKeyException;
import com.tld.exception.InvalidApiKeyException;
import com.tld.exception.InvalidJsonFormatException;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.service.CompanyService;
import com.tld.service.SensorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/sensor")
@RequiredArgsConstructor
public class SensorController {

	private final SensorService sensorService;

	private final CompanyService companyService;

	@PostMapping("add")
	public ResponseEntity<?> addSensor(@RequestBody SensorDTO sensorDTO,
			@RequestHeader("company_api_key") String companyApiKey) {
		System.out.println("apiKey " + companyApiKey + " SensorDTO" + sensorDTO.getSensorName());

		Optional<CompanyDTO> companyDTO = companyService.getCompanyByApiKey(companyApiKey);

		if (companyDTO.isEmpty()) {
			throw new InvalidApiKeyException("No tienes una llave válida.");
		}

		try {
			return new ResponseEntity<>(sensorService.addSensor(sensorDTO), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("llave duplicada")) {
				throw new DuplicateKeyException("Sensor", "sensor_api_key");
			} else {
				throw new InvalidJsonFormatException("El JSON presenta errores de datos o formato.");
			}
		}
	}

}
