package com.tld.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.tld.dto.SensorDTO;
import com.tld.dto.SensorInfoDTO;
import com.tld.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensorControllerTest {
	
	 @InjectMocks
	    private SensorController sensorController;

	    @Mock
	    private SensorService sensorService;

	    private MockMvc mockMvc;

	    @BeforeEach
	    public void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(sensorController).build();
	    }

	    @Test
	    public void testAddSensor() throws Exception {
	        // Crear el objeto SensorDTO para la solicitud
	        SensorDTO sensorDTO = new SensorDTO();
	        sensorDTO.setSensorApiKey("mockApiKey");

	        // Crear el objeto SensorInfoDTO para la respuesta esperada
	        SensorInfoDTO sensorInfoDTO = new SensorInfoDTO();
	        sensorInfoDTO.setSensorId(1L); // o los valores correspondientes

	        // Simular la respuesta del servicio
	        when(sensorService.addSensor(any(SensorDTO.class))).thenReturn(sensorInfoDTO);

	        // Realizar la solicitud POST y verificar la respuesta
	        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sensor")
	                .header("company_api_key", "mockApiKey")
	                .contentType("application/json")
	                .content("{\"sensorId\": 1, \"sensorName\": \"Sensor1\"}"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.sensorId").value(1L));
	    }
	    @Test
	    public void testUpdateSensor() throws Exception {
	        // Crear un SensorInfoDTO, ya que es el tipo que se espera de la respuesta
	        SensorInfoDTO sensorInfoDTO = new SensorInfoDTO();
	        sensorInfoDTO.setSensorId(1L);
	        sensorInfoDTO.setSensor_api_key("mockApiKey"); // Asegúrate de usar el nombre correcto del campo
	        sensorInfoDTO.setSensorName("UpdatedSensor");

	        // Configurar el stub para devolver el objeto adecuado
	        when(sensorService.updateSensor(anyString(), any(SensorDTO.class))).thenReturn(sensorInfoDTO);

	        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/sensor/1")
	                .header("company_api_key", "mockApiKey")
	                .contentType("application/json")
	                .content("{\"sensorName\": \"UpdatedSensor\"}"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.sensorId").value(1L))
	                .andExpect(jsonPath("$.sensor_api_key").value("mockApiKey")) // Usa sensor_api_key
	                .andExpect(jsonPath("$.sensorName").value("UpdatedSensor"));
	    }

	    @Test
	    public void testGetSensors() throws Exception {
	        // Crear una lista de SensorInfoDTO, ya que es el tipo que se espera de la respuesta
	        SensorInfoDTO sensorInfoDTO = new SensorInfoDTO();
	        sensorInfoDTO.setSensor_api_key("mockApiKey");  // Usar el nombre correcto del campo
	        sensorInfoDTO.setSensorId(1L);
	        sensorInfoDTO.setSensorName("Sensor1");

	        // Configurar el stub para devolver una lista de SensorInfoDTO
	        when(sensorService.getSensors(anyString(), anyString(), anyString())).thenReturn(List.of(sensorInfoDTO));

	        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sensor")
	                .param("field", "name")
	                .param("value", "Sensor1")
	                .header("company_api_key", "mockApiKey"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].sensor_api_key").value("mockApiKey"))  // Usar el nombre correcto del campo
	                .andExpect(jsonPath("$[0].sensorName").value("Sensor1"));
	    }

	    @Test
	    public void testDeleteSensor() throws Exception {
	        when(sensorService.deleteSensor(eq(1L), anyString())).thenReturn("Sensor deleted");

	        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/sensor/1")
	                .header("company_api_key", "mockApiKey"))
	                .andExpect(status().isOk())
	                .andExpect(content().string("Sensor deleted"));
	    }

}
