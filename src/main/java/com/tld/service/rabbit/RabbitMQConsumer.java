package com.tld.service.rabbit;

import java.util.logging.Level;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tld.dto.MeasurementDTO;
import com.tld.mapper.MeasurementMapper;
import com.tld.service.impl.MeasurementServiceImpl;
import com.tld.util.LogUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
	//private final RestTemplate restTemplate;	
	private final MeasurementServiceImpl measurementServiceImpl;
	private final MeasurementMapper measurementMapper;
	
	//se puede dejar en properties sensor.api.url=http://localhost:8080/api/v1/sensordata 
    //private final String URL_DESTINO = "http://localhost:8080/api/v1/sensordata";  // Cambia esto por tu endpoint real

 /*   @RabbitListener(queues = "sensor_cola")
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
    */

    @RabbitListener(queues = "sensor_cola")
    public void receiveSensorData(String message) {
    	LogUtil.log(RabbitMQConsumer.class, Level.INFO, "Solicitud recibida en consumer");             
    	 try {
        String[] split = message.split("\\|", 2);
        if (split.length < 1) {
            System.err.println("❌ Mensaje mal formado, no tiene API Key");
            throw new com.tld.exception.MissingParameterException("Faltan parametros en peticion");
        }               
        
        String sensorApiKey = split[0];
        String json = split[1]; 
        
        
        try {	
        measurementServiceImpl.addSensorData(measurementMapper.fromJsonToDTO(json), sensorApiKey);      
        } catch (com.tld.exception.EntityNotFoundException e) {
            LogUtil.log(RabbitMQConsumer.class, Level.WARNING, "Sensor o API Key no encontrado: " + sensorApiKey);
            // Aquí podrías manejarlo de alguna manera, tal vez enviar una respuesta de error, si corresponde
            // También podrías re-throw la excepción si deseas que se propague
           return ;  // O simplemente podrías hacer un return si no deseas re-throw
        }
		 } catch (Exception e) {
	        // Logea el error completo para ayudar en la depuración
	        LogUtil.log(RabbitMQConsumer.class, Level.SEVERE, "Error al procesar el mensaje");
	        return;  // Rethrow si necesitas que el contenedor de RabbitMQ sepa que falló
	    }
                
    }
}