package com.tld.controller;

import com.tld.service.rabbit.RabbitMQProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQControllerTest {
	
	 @Mock
	    private RabbitMQProducer producer;

	    @InjectMocks
	    private RabbitMQController rabbitMQController;
	    
	  

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	
	  
//	    @Test
//	    void testSendMessage_Failure() {
//	        // Arrange
//	        String message = "Test Message";
//	        String apiKey = "testApiKey";
//	        String expectedMessage = apiKey + "|" + message;
//	        when(producer.sendMessage(expectedMessage)).thenReturn(false);
//
//	        // Act
//	        ResponseEntity<String> response = rabbitMQController.sendMessage(message, apiKey);
//
//	        // Assert
//	        assertEquals(503, response.getStatusCodeValue());
//	        assertEquals("No se pudo conectar a RabbitMQ. Intenta más tarde.", response.getBody());
//	    }
//
//	    @Test
//	    void testSendMessage_Exception() {
//	        // Arrange
//	        String message = "Test Message";
//	        String apiKey = "testApiKey";
//	        String expectedMessage = apiKey + "|" + message;
//	        
//	        // Configurar el mock para lanzar una excepción (simula un error inesperado)
//	        when(producer.sendMessage(expectedMessage)).thenThrow(new RuntimeException("Unexpected error"));
//
//	        // Act
//	        ResponseEntity<String> response = rabbitMQController.sendMessage(message, apiKey);
//
//	        // Assert
//	        assertEquals(503, response.getStatusCodeValue());  // Verifica que el código de estado sea 503
//	        assertEquals("No se pudo conectar a RabbitMQ. Intenta más tarde.", response.getBody());  // Verifica el mensaje de error
//	    }
	    

}
