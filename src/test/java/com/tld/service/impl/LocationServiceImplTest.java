package com.tld.service.impl;

import com.tld.dto.LocationDTO;
import com.tld.entity.Users;
import com.tld.jpa.repository.CityRepository;
import com.tld.jpa.repository.LocationRepository;
import com.tld.jpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LocationServiceImplTest {

    @InjectMocks
    private LocationServiceImpl locationServiceImpl;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private UserRepository userRepository;

    private Users authenticatedUser;
    private LocationDTO locationDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando un usuario simulado
        authenticatedUser = new Users();
        authenticatedUser.setUserName("testUser");

        // Inicializando un DTO de Location
        locationDTO = new LocationDTO();
        locationDTO.setLocationAddress("Test Address");
        locationDTO.setCityId(1);
        locationDTO.setLocationMeta("Test Meta");
    }

  

    

    @Test
    public void testUpdateLocationNotFound() {
        // Mock de la autenticación
        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(authenticatedUser));

        // Mock de no encontrar la ubicación
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al método de prueba y afirmación de excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            locationServiceImpl.updateLocation(1L, locationDTO);
        });

        assertEquals("Location no encontrada con ID: 1", exception.getMessage());
    }

    

    @Test
    public void testDeleteLocationNotFound() {
        // Mock de la autenticación
        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(authenticatedUser));

        // Mock de no encontrar la ubicación
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamada al método de prueba y afirmación de excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            locationServiceImpl.deleteLocation(1L);
        });

        assertEquals("No existe ninguna location con id: 1", exception.getMessage());
    }
}