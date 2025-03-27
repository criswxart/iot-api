package com.tld.controller;
import com.tld.dto.LocationDTO;
import com.tld.dto.LocationInfoDTO;
import com.tld.service.LocationService;
import com.tld.util.LogUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationControllerTest {
	 @InjectMocks
	    private LocationController locationController;

	    @Mock
	    private LocationService locationService;

	    private MockMvc mockMvc;

	    @BeforeEach
	    public void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
	    }

	    @Test
	    void testAddLocation_Success() throws Exception {
	        // Crear el objeto LocationInfoDTO usando los setters
	        LocationInfoDTO locationInfoDTO = new LocationInfoDTO();
	        locationInfoDTO.setLocationId(1L);
	        locationInfoDTO.setCompanyName("Iquique");  // Usar companyName en lugar de companyId
	        locationInfoDTO.setLocationAddress("CalleX");
	        locationInfoDTO.setCityName("Talca"); // Usar cityName en lugar de cityId
	        locationInfoDTO.setLocationMeta("Meta");

	        // Mockear la respuesta del servicio para que devuelva un LocationInfoDTO
	        when(locationService.addLocation(any(LocationDTO.class))).thenReturn(locationInfoDTO);

	        // Ejecutar la prueba
	        mockMvc.perform(post("/api/v1/location")
	                .contentType("application/json")
	                .content("{\"locationId\": 1, \"companyName\": \"Iquique\", \"locationAddress\": \"CalleX\", \"cityName\": \"Talca\", \"locationMeta\": \"Meta\"}"))
	                .andExpect(status().isOk())
	                .andExpect(content().json("{\"locationId\": 1, \"companyName\": \"Iquique\", \"locationAddress\": \"CalleX\", \"cityName\": \"Talca\", \"locationMeta\": \"Meta\"}"));
	    }

	    @Test
	    void testGetLocations_Success() throws Exception {
	        // Crear el objeto LocationInfoDTO y asignar los valores utilizando setters
	        LocationInfoDTO locationInfoDTO = new LocationInfoDTO();
	        locationInfoDTO.setLocationId(1L);
	        locationInfoDTO.setLocationAddress("CalleX");
	        locationInfoDTO.setLocationMeta("Meta");
	        locationInfoDTO.setCompanyName("Iquique"); // Ajustar el campo
	        locationInfoDTO.setCityName("Talca"); // Ajustar el campo

	        // Mockear el comportamiento del servicio para devolver una lista de LocationInfoDTO
	        when(locationService.getLocations("ciudad", "iquique")).thenReturn(List.of(locationInfoDTO));

	        // Ejecutar la solicitud GET
	        mockMvc.perform(get("/api/v1/location")
	                .param("field", "ciudad")
	                .param("value", "iquique"))
	                .andExpect(status().isOk())
	                .andExpect(content().json("[{\"locationId\": 1, \"companyName\": \"Iquique\", \"locationAddress\": \"CalleX\", \"cityName\": \"Talca\", \"locationMeta\": \"Meta\"}]"));
	    }

	  

	    @Test
	    void testDeleteLocation_Success() throws Exception {
	        when(locationService.deleteLocation(1L)).thenReturn("Location deleted successfully");

	        mockMvc.perform(delete("/api/v1/location/1"))
	                .andExpect(status().isOk())
	                .andExpect(content().string("Location deleted successfully"));

	        verify(locationService).deleteLocation(1L);  // Verify that the service method was called
	    }
}
