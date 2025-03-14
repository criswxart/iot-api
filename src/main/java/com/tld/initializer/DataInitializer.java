package com.tld.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
	  @Autowired
      private JdbcTemplate jdbcTemplate;
	  
	    @PostConstruct
	    public void init() {
	    	
	        if (!doesExistAnyRecord("country")) {
	        	jdbcTemplate.update("INSERT INTO country (country_id, country_name) VALUES (1,'Chile'), (2,'Peru')");
	        	jdbcTemplate.update("INSERT INTO region (region_id, region_name, country_id) VALUES (1, 'Región de Arica y Parinacota', 1), (2, 'Región de Tarapacá', 1), (3, 'Región de Antofagasta', 1), (4, 'Región de Atacama', 1), (5, 'Región de Coquimbo', 1), (6, 'Región de Valparaíso', 1), (7, 'Región Metropolitana de Santiago', 1), (8, 'Región del Libertador General Bernardo O’Higgins', 1), (9, 'Región de Maule', 1), (10, 'Región de Ñuble', 1), (11, 'Región de Biobío', 1), (12, 'Región de La Araucanía', 1), (13, 'Región de Los Ríos', 1), (14, 'Región de Los Lagos', 1), (15, 'Región de Aysén del General Carlos Ibáñez del Campo', 1), (16, 'Región de Magallanes y de la Antártica Chilena', 1), (17, 'Amazonas', 2), (18, 'Áncash', 2), (19, 'Apurímac', 2), (20, 'Arequipa', 2), (21, 'Ayacucho', 2), (22, 'Cajamarca', 2), (23, 'Callao', 2), (24, 'Cusco', 2), (25, 'Huancavelica', 2), (26, 'Huánuco', 2), (27, 'Ica', 2), (28, 'Junín', 2), (29, 'La Libertad', 2), (30, 'Lambayeque', 2), (31, 'Lima', 2), (32, 'Loreto', 2), (33, 'Madre de Dios', 2), (34, 'Moquegua', 2), (35, 'Pasco', 2), (36, 'Piura', 2), (37, 'Puno', 2), (38, 'San Martín', 2), (39, 'Tacna', 2), (40, 'Tumbes', 2), (41, 'Ucayali', 2)");
	        	jdbcTemplate.update("INSERT INTO city (city_name, region_id) VALUES ('Lima', 31), ('Arequipa', 20), ('Trujillo', 29), ('Chiclayo', 28), ('Piura', 36), ('Cusco', 24), ('Iquitos', 32), ('Huancayo', 13), ('Tacna', 39), ('Puno', 37), ('Calama', 3), ('Chuquicamata', 3), ('Copiapó', 4), ('Tierra Amarilla', 4), ('El Salvador', 4), ('Vallenar', 4), ('Diego de Almagro', 4), ('Concepción', 11), ('Temuco', 12), ('Valdivia', 13), ('Antofagasta', 3), ('Puerto Montt', 14), ('Castro', 14), ('Coyhaique', 15), ('Punta Arenas', 16), ('Iquique', 2), ('Arica', 1)");
	        }     
	        
	        if (!doesExistAnyRecord("category")) {
	        	jdbcTemplate.update("INSERT INTO category (category_id, category_name) VALUES (1,'ESP32'), (2,'Zigbee')");
	        }
	        
	        if (!doesExistAnyRecord("role")) {
	        	jdbcTemplate.update("INSERT INTO role (role_id, role_name) VALUES (1,'Administrador'), (2,'Operario')");
	        }
	        
//	        if (!doesExistAnyRecord("users")) {
//	        	jdbcTemplate.update("INSERT INTO users (user_name, user_password, role_id, user_created_at, user_active) VALUES ('administrator','123456', 1, 10800, TRUE)	");
//	        }
	        
	        if (!doesExistAnyRecord("company")) {
	        	jdbcTemplate.update("INSERT INTO company (company_name, company_api_key, company_created_by, company_created_at, company_active, company_modified_by, company_modified_at) VALUES ('Grupo 5', 'API-12345-XYZ', 1, 10800, true, 1, 10800)");
	        }
	        
	    }
	    
	    public boolean doesExistAnyRecord(String tableName) {	    	
	        if(jdbcTemplate.queryForObject( "SELECT COUNT(*) FROM "+tableName, Integer.class)>0) {
	        	return true;
	        }
	        return false;
	    }

}
