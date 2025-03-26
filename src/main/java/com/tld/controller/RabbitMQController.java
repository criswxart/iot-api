package com.tld.controller;

import java.util.Random;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tld.service.rabbit.RabbitMQProducer;

@RestController
@RequestMapping("/api/v1/rabbit")
public class RabbitMQController {

    private final RabbitMQProducer producer;
    
   

    public RabbitMQController(RabbitMQProducer producer) {
        this.producer = producer;
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> sendMessage(@RequestBody String message, @RequestHeader("sensor_api_key") String sensorApiKey) {
        try {
            String messageWithApiKey = sensorApiKey + "|" + message; // Concatenar API Key con el mensaje

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