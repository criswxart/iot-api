package com.tld.service.rabbit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQProducerTest {
	 @Mock
	    private RabbitTemplate rabbitTemplate;

	    @Mock
	    private RetryTemplate retryTemplate;

	    @InjectMocks
	    private RabbitMQProducer rabbitMQProducer;

	    @BeforeEach
	    void setUp() {
	        // Inicialización de los mocks
	        rabbitMQProducer = new RabbitMQProducer(rabbitTemplate);
	    }

	    @Test
	    void testSendMessage_success() throws Throwable {
	        String message = "Test message";

	        // Configura el comportamiento del mock para el retryTemplate
	        // Usa doAnswer para simular que retryTemplate.execute() no lanza excepciones
	        doAnswer(invocation -> {
	            // Simula que no ocurre ninguna excepción y se ejecuta normalmente
	            return null;  // Si el método esperado no tiene valor de retorno, devuelve null
	        }).when(retryTemplate).execute(Mockito.any());

	        // Configura el comportamiento del mock para el rabbitTemplate
	        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString());

	        // Llama al método que queremos probar
	        boolean result = rabbitMQProducer.sendMessage(message);

	        // Verifica que el mensaje haya sido enviado a RabbitMQ
	        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), eq(message));

	        // Asegúrate de que el método devuelve true
	        assert(result);
	    }
	   
}

	   
