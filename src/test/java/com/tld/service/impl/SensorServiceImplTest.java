package com.tld.service.impl;
import com.tld.dto.SensorDTO;
import com.tld.dto.SensorInfoDTO;
import com.tld.model.Company;
import com.tld.model.Location;
import com.tld.model.Sensor;
import com.tld.model.Category;
import com.tld.service.impl.SensorServiceImpl;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.EntityNotFoundException;

import com.tld.jpa.repository.SensorRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.CategoryRepository;
import com.tld.mapper.SensorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTest {
	

    @Mock
    private SensorRepository sensorRepository;
    
    @Mock(lenient = true)
    LocationRepository locationRepository;
    
    @Mock
    private CompanyRepository companyRepository;
    
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    

    @Test
    void testAddSensor_InvalidCompany_ThrowsException() {
        // Arrange
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setSensorApiKey("invalid-api-key");
        
        when(companyRepository.findByCompanyApiKey("invalid-api-key")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> sensorService.addSensor(sensorDTO));
    }

    @Test
    void testAddSensor_InvalidLocation_ThrowsException() {
        // Configurar el stubbing para devolver una respuesta específica
        when(locationRepository.findIfLocationAndCompanyAreOk(null, 1L)).thenReturn((short) 0);
        
        // Crear el DTO de prueba
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setLocationId(null);  // o el valor adecuado que cause el error

        // Probar que se lanza la excepción
        assertThrows(EntityNotFoundException.class, () -> sensorService.addSensor(sensorDTO));
    }
    
    
    @Test
    void testUpdateSensor_SensorNotFound_ThrowsException() {
        // Arrange
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setSensorId(1L);

        when(sensorRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> sensorService.updateSensor("valid-api-key", sensorDTO));
    }
    


    @Test
    void testDeleteSensor_ValidData_SensorInactivated() {
        // Arrange
        Long sensorId = 1L;
        String companyApiKey = "valid-api-key";
        Sensor sensor = new Sensor();
        sensor.setSensorId(sensorId);
        sensor.setSensorApiKey(companyApiKey);

        when(sensorRepository.findById(sensorId)).thenReturn(Optional.of(sensor));
        
        // Act
        String result = sensorService.deleteSensor(sensorId, companyApiKey);

        // Assert
        assertEquals("Has inactivado el sensor", result);
        verify(sensorRepository, times(1)).save(sensor);
    }

    @Test
    void testDeleteSensor_SensorNotFound_ReturnMessage() {
        // Arrange
        Long sensorId = 1L;
        String companyApiKey = "valid-api-key";
        
        when(sensorRepository.findById(sensorId)).thenReturn(Optional.empty());

        // Act
        String result = sensorService.deleteSensor(sensorId, companyApiKey);

        // Assert
        assertEquals("No puedes borrar lo que nunca existio", result);
    }

}
