package com.tld.service.rabbit;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQConsumerTest {
//	@Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private RabbitMQConsumer rabbitMQConsumer;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    
//
//    @Test
//    public void testReceiveMessage_invalidMessage() {
//        // Datos de prueba con formato incorrecto
//        String message = "mockApiKey"; // Mensaje mal formado
//
//        // Llama al método que quieres probar
//        rabbitMQConsumer.receiveMessage(message);
//
//        // Verifica que el método no haya intentado enviar una solicitud HTTP
//        verify(restTemplate, never()).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
//    }

}
