package com.tld.service.impl;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.tld.dto.SensorDataDTO;
import com.tld.entity.Sensor;
import com.tld.entity.SensorData;
import com.tld.model.id.SensorDataId;
import com.tld.mapper.SensorDataMapper;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.SensorDataRepository;
import com.tld.jpa.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.persistence.EntityNotFoundException;

class SensorDataServiceImplTest {

    @Mock
    private SensorDataRepository sensorDataRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorDataServiceImpl sensorDataService;

    private SensorDataDTO sensorDataDTO;
    private Sensor sensor;
    
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        
//        sensor = new Sensor();
//        sensor.setSensorApiKey("valid-api-key");
//        
//        sensorDataDTO = new SensorDataDTO();
//        sensorDataDTO.setSensorApiKey("valid-api-key");
//        // Aquí puedes configurar otros campos de sensorDataDTO según tu necesidad
//    }
//
//
//
//    @Test
//    void testAddSensorData_InvalidApiKey_ThrowsException() {
//        // Arrange
//        sensorDataDTO.setSensorApiKey("invalid-api-key"); // Set the invalid API key here
//        when(sensorRepository.findBySensorApiKey("invalid-api-key")).thenReturn(Optional.empty());
//
//        // Act & Assert
//        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
//            sensorDataService.addSensorData(sensorDataDTO);
//        });
//
//        // Assert exception message
//        assertTrue(thrown.getMessage().contains("No existe sensor con la api key entragada"));
//
//        // Verify the interaction with the repository
//        verify(sensorRepository, times(1)).findBySensorApiKey("invalid-api-key");  // This should match the input API key
//        verify(sensorDataRepository, never()).save(any(SensorData.class));  // Verify save was not called
//    }
    
    
   

    
    
}
