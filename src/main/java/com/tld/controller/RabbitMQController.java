package com.tld.controller;

import java.util.Random;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tld.service.rabbit.RabbitMQProducer;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/api/v1/rabbit")
@Tag(name = "RabbitMQ Controller", description = "Gestion de Rabbit")
public class RabbitMQController {

    private final RabbitMQProducer producer;   

    public RabbitMQController(RabbitMQProducer producer) {
        this.producer = producer;
    }
       
    @PostMapping("/add")
    @Operation(summary = "Enviar datos del sensor a RabbitMQ", description = "Este endpoint permite enviar un mensaje con datos del sensor a RabbitMQ. La API key del sensor se puede incluir opcionalmente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensaje enviado con éxito", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error inesperado al enviar el mensaje")
    })
    public ResponseEntity<String> sendSensorData(@RequestBody String message, @RequestHeader(value ="sensor_api_key", required = false) String sensorApiKey) {
        try {
            String messageWithApiKey = sensorApiKey + "|" + message; // Concatenar API Key con el mensaje
            System.out.println( messageWithApiKey);

            boolean messageSent = producer.sendMessage(messageWithApiKey);
            
            if (messageSent) {
                return new ResponseEntity<>("Mensaje enviado con éxito", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se pudo conectar a RabbitMQ. Intenta más tarde.", HttpStatus.SERVICE_UNAVAILABLE);
            }
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return new ResponseEntity<>("Error inesperado. Intenta más tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    @PostMapping("/masivo/{nTimes}")
    @Operation(summary = "Enviar mensaje múltiples veces a RabbitMQ", description = "Este endpoint permite enviar un mensaje a RabbitMQ un número específico de veces (nTimes), con un retraso aleatorio entre los envíos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensajes enviados exitosamente", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Error inesperado al enviar los mensajes")
    })
    public String sendMessageNTtimes(@PathVariable Integer nTimes, @RequestBody String message, @RequestHeader("sensor_api_key") String sensorApiKey) {
    	String messageWithApiKey = sensorApiKey + "|" + message; // Concatenar API Key con el mensaje
    	Random random = new Random();
    	
    	for (int i=0; i < nTimes; i++ ) {
    		producer.sendMessage(messageWithApiKey);  		
			try {
				Thread.sleep(random.nextInt(1,31)* 1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	}
       return " Se han enviado los "+nTimes+" mensajes a RabbitMQ con API Key";
    	
    }
    
    
}