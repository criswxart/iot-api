package com.tld.service.rabbit;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
	
	private final RabbitTemplate rabbitTemplate;
    private final String queueName = "sensor_cola";  // Asegúrate de que esta es la cola correcta
    private final RetryTemplate retryTemplate;
    
    // Constructor
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

        // Configurar RetryTemplate con un ExponentialBackOffPolicy
        this.retryTemplate = new RetryTemplate();
        
        // Configuración de la política de retroceso fijo
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(3000);  // Tiempo fijo entre intentos: 3 segundos
        this.retryTemplate.setBackOffPolicy(backOffPolicy);
        
        //Re intentos
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);  // Número máximo de intentos (puedes ajustarlo según sea necesario)
        this.retryTemplate.setRetryPolicy(retryPolicy);
        
        // Límite de tiempo total para los reintentos
        TimeoutRetryPolicy timeoutPolicy = new TimeoutRetryPolicy();
        timeoutPolicy.setTimeout(2000);  // Tiempo máximo en milisegundos (10 segundos)
        this.retryTemplate.setRetryPolicy(timeoutPolicy);
    }

    // Método para enviar mensajes con reintentos
    public boolean sendMessage(String message) {
        try {
            // Ejecutar el envío del mensaje con reintentos automáticos
            retryTemplate.execute(context -> {
                rabbitTemplate.convertAndSend(queueName, message);
                System.out.println("📤 Mensaje enviado a RabbitMQ: " + message);
                return null;  // No necesitamos un valor de retorno
            });
            return true;  // Mensaje enviado con éxito
        } catch (AmqpConnectException e) {
            System.out.println("Error de conexión con RabbitMQ: " + e.getMessage());
            return false; // No se pudo conectar a RabbitMQ
        } catch (AmqpIOException e) {
            System.out.println("Error de IO con RabbitMQ: " + e.getMessage());
            return false; // Error de IO, no se pudo enviar
        } catch (ListenerExecutionFailedException e) {
            System.out.println("Fallo en la ejecución del listener de RabbitMQ: " + e.getMessage());
            return false; // Error al ejecutar el listener
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return false; // Error inesperado
        }
    }
/*
    private final RabbitTemplate rabbitTemplate;
    private final String queueName = "sensor_cola";  // Asegúrate de que esta es la cola correcta

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("📤 Mensaje enviado a RabbitMQ: " + message);
    }
    
    */
}