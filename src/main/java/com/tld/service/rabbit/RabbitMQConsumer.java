package com.tld.service.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RabbitMQConsumer {
	private final RestTemplate restTemplate = new RestTemplate();
	
	//se puede dejar en properties sensor.api.url=http://localhost:8080/api/v1/sensordata 
    private final String URL_DESTINO = "http://localhost:8080/api/v1/sensordata";  // Cambia esto por tu endpoint real

    @RabbitListener(queues = "sensor_cola")
    public void receiveMessage(String message) {
        System.out.println("📥 Mensaje recibido: " + message);
        
       
        String[] split = message.split("\\|", 2);
        if (split.length < 2) {
            System.err.println("❌ Mensaje mal formado, no tiene API Key");
            return;
        }
        
        String sensorApiKey = split[0];
        String json = split[1];

        HttpHeaders headers = new HttpHeaders();
        headers.set("sensor_api_key", sensorApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        
        //Enviar la solicitud POST al endpoint
        try {
        ResponseEntity<String> response = restTemplate.exchange(URL_DESTINO, HttpMethod.POST, requestEntity, String.class);       
        //String respuesta = restTemplate.postForObject(URL_DESTINO, json, String.class);
        System.out.println("📤 Respuesta del endpoint: " + response);
	    } catch (Exception e) {
	       System.out.println("❌ Error al enviar datos al endpoint: {}"+ e.getMessage());
	    }
    }
}
