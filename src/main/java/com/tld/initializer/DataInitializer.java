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
	    	    jdbcTemplate.update("""
	    	        INSERT INTO country (country_id, country_name, country_is_active, country_created_at, country_modified_at) VALUES
	    	        (1, 'Chile', TRUE, now(), now()),
	    	        (2, 'Peru', TRUE, now(), now())
	    	    """);

	    	    jdbcTemplate.update("""
	    	        INSERT INTO region (region_id, region_name, country_id, region_is_active, region_created_at, region_modified_at) VALUES
	    	        (1, 'Región de Arica y Parinacota', 1, TRUE, now(), now()), 
	    	        (2, 'Región de Tarapacá', 1, TRUE, now(), now()),
	    	        (3, 'Región de Antofagasta', 1, TRUE, now(), now()),
	    	        (4, 'Región de Atacama', 1, TRUE, now(), now()), 
	    	        (5, 'Región de Coquimbo', 1, TRUE, now(), now()), 
	    	        (6, 'Región de Valparaíso', 1, TRUE, now(), now()), 
	    	        (7, 'Región Metropolitana de Santiago', 1, TRUE, now(), now()), 
	    	        (8, 'Región del Libertador General Bernardo O’Higgins', 1, TRUE, now(), now()), 
	    	        (9, 'Región de Maule', 1, TRUE, now(), now()), 
	    	        (10, 'Región de Ñuble', 1, TRUE, now(), now()), 
	    	        (11, 'Región de Biobío', 1, TRUE, now(), now()), 
	    	        (12, 'Región de La Araucanía', 1, TRUE, now(), now()), 
	    	        (13, 'Región de Los Ríos', 1, TRUE, now(), now()), 
	    	        (14, 'Región de Los Lagos', 1, TRUE, now(), now()), 
	    	        (15, 'Región de Aysén del General Carlos Ibáñez del Campo', 1, TRUE, now(), now()), 
	    	        (16, 'Región de Magallanes y de la Antártica Chilena', 1, TRUE, now(), now()), 
	    	        (17, 'Amazonas', 2, TRUE, now(), now()), 
	    	        (18, 'Áncash', 2, TRUE, now(), now()), 
	    	        (19, 'Apurímac', 2, TRUE, now(), now()), 
	    	        (20, 'Arequipa', 2, TRUE, now(), now()), 
	    	        (21, 'Ayacucho', 2, TRUE, now(), now()), 
	    	        (22, 'Cajamarca', 2, TRUE, now(), now()),
	    	        (23, 'Callao', 2, TRUE, now(), now()), 
	    	        (24, 'Cusco', 2, TRUE, now(), now()), 
	    	        (25, 'Huancavelica', 2, TRUE, now(), now()), 
	    	        (26, 'Huánuco', 2, TRUE, now(), now()), 
	    	        (27, 'Ica', 2, TRUE, now(), now()), 
	    	        (28, 'Junín', 2, TRUE, now(), now()), 
	    	        (29, 'La Libertad', 2, TRUE, now(), now()), 
	    	        (30, 'Lambayeque', 2, TRUE, now(), now()), 
	    	        (31, 'Lima', 2, TRUE, now(), now()), 
	    	        (32, 'Loreto', 2, TRUE, now(), now()), 
	    	        (33, 'Madre de Dios', 2, TRUE, now(), now()), 
	    	        (34, 'Moquegua', 2, TRUE, now(), now()), 
	    	        (35, 'Pasco', 2, TRUE, now(), now()), 
	    	        (36, 'Piura', 2, TRUE, now(), now()), 
	    	        (37, 'Puno', 2, TRUE, now(), now()), 
	    	        (38, 'San Martín', 2, TRUE, now(), now()), 
	    	        (39, 'Tacna', 2, TRUE, now(), now()), 
	    	        (40, 'Tumbes', 2, TRUE, now(), now()), 
	    	        (41, 'Ucayali', 2, TRUE, now(), now());
	    	    """);

	    	    jdbcTemplate.update("""
	    	        INSERT INTO city (city_name, region_id, city_is_active, city_created_at, city_modified_at) VALUES
	    	        ('Lima', 31, true, now(), now()),
	    	        ('Arequipa', 20, true, now(), now()),
	    	        ('Trujillo', 29, true, now(), now()),
	    	        ('Chiclayo', 28, true, now(), now()),
	    	        ('Piura', 36, true, now(), now()),
	    	        ('Cusco', 24, true, now(), now()),
	    	        ('Iquitos', 32, true, now(), now()),
	    	        ('Huancayo', 13, true, now(), now()),
	    	        ('Tacna', 39, true, now(), now()),
	    	        ('Puno', 37, true, now(), now()),
	    	        ('Calama', 3, true, now(), now()),
	    	        ('Chuquicamata', 3, true, now(), now()),
	    	        ('Copiapó', 4, true, now(), now()),
	    	        ('Tierra Amarilla', 4, true, now(), now()),
	    	        ('El Salvador', 4, true, now(), now()),
	    	        ('Vallenar', 4, true, now(), now()),
	    	        ('Diego de Almagro', 4, true, now(), now()),
	    	        ('Concepción', 11, true, now(), now()),
	    	        ('Temuco', 12, true, now(), now()),
	    	        ('Valdivia', 13, true, now(), now()),
	    	        ('Antofagasta', 3, true, now(), now()),
	    	        ('Puerto Montt', 14, true, now(), now()),
	    	        ('Castro', 14, true, now(), now()),
	    	        ('Coyhaique', 15, true, now(), now()),
	    	        ('Punta Arenas', 16, true, now(), now()),
	    	        ('Iquique', 2, true, now(), now()),
	    	        ('Arica', 1, true, now(), now());
	    	    """);
	    	}     

	    	if (!doesExistAnyRecord("category")) {
	    	    jdbcTemplate.update("""
	    	        INSERT INTO category (category_id, category_name, category_is_active, category_created_at, category_modified_at) VALUES
	    	        (1, 'ESP32', true, now(), now()), 
	    	        (2, 'Zigbee', true, now(), now());
	    	    """);
	    	}

	    	if (!doesExistAnyRecord("role")) {
	    	    jdbcTemplate.update("""
	    	        INSERT INTO role (role_id, role_name, role_is_active, role_created_at, role_modified_at) VALUES
	    	        (1, 'Administrador', true, now(), now()), 
	    	        (2, 'Operario', true, now(), now());
	    	    """);
	    	}

	    	if (!doesExistAnyRecord("permission")) {
	    	    jdbcTemplate.update("""
	    	        INSERT INTO permission (permission_id, permission_name, permission_created_at, permission_modified_at) VALUES
	    	        (1, 'Insertar', now(), now()), 
	    	        (2, 'Actualizar', now(), now()), 
	    	        (3, 'Borrar', now(), now()), 
	    	        (4, 'Leer', now(), now());
	    	    """);
	    	}

	        /*Admin tiene acceso a todo, operario solo a leer*/
	        if (!doesExistAnyRecord("role_permission")) {
	        	jdbcTemplate.update("INSERT INTO role_permission (role_id, permission_id) VALUES (1,1),(1,2),(1,3),(1,4), (2,4) ;");
	        }      
	        
	        if (!doesExistAnyRecord("users")) {
	            jdbcTemplate.update("""
	                INSERT INTO users (
	                    account_non_expired, 
	                    account_non_locked, 
	                    credentials_non_expired, 
	                    user_enabled, 
	                    user_name, 
	                    user_password, 
	                    user_created_at, 
	                    user_modified_at
	                ) VALUES 
	                (TRUE, TRUE, TRUE, TRUE, 'sebastian', '{noop}123456', NOW(), NOW()),
	                (TRUE, TRUE, TRUE, TRUE, 'luis', '{noop}123456', NOW(), NOW()),
	                (TRUE, TRUE, TRUE, TRUE, 'cristian', '{noop}123456', NOW(), NOW()),
	                (TRUE, TRUE, TRUE, TRUE, 'manuel', '{noop}123456', NOW(), NOW()),
	                (TRUE, TRUE, TRUE, TRUE, 'alexis', '{noop}123456', NOW(), NOW());
	            """);
	        }

	        if (!doesExistAnyRecord("users_role")) {
	        	jdbcTemplate.update("""
	        			 INSERT INTO users_role (user_id,role_id)  
	        			     		      VALUES   ((select user_id from users where user_name='sebastian'),1),
			        			     		       ((select user_id from users where user_name='luis'),1),
			        			     		       ((select user_id from users where user_name='cristian'),1),
			        			     		       ((select user_id from users where user_name='manuel'),1),
			        			     		       ((select user_id from users where user_name='alexis'),2);			
	        				""");	        			
	        } 
	        
	        if (!doesExistAnyRecord("company")) {
	            jdbcTemplate.update("""
	                INSERT INTO company (
	                    company_name, 
	                    company_api_key, 
	                    company_created_by, 
	                    company_is_active, 
	                    company_modified_by, 
	                    company_created_at, 
	                    company_modified_at
	                ) VALUES 
	                ('company AAA', 'API-12345-ABS', (SELECT user_id FROM users WHERE user_name='sebastian'), TRUE, (SELECT user_id FROM users WHERE user_name='sebastian'), NOW(), NOW()),
	                ('company BBB', 'API-12345-DEP', (SELECT user_id FROM users WHERE user_name='luis'), TRUE, (SELECT user_id FROM users WHERE user_name='luis'), NOW(), NOW()),
	                ('company CCC', 'API-12345-ASR', (SELECT user_id FROM users WHERE user_name='cristian'), TRUE, (SELECT user_id FROM users WHERE user_name='cristian'), NOW(), NOW());
	            """);
	        }
	        
	        if (!doesFunctionExist("generate_sensor_correlative")) {
	        	 jdbcTemplate.execute("""
	 	        		CREATE OR REPLACE FUNCTION generate_sensor_correlative()
	 			        RETURNS TRIGGER AS $$
	 			        DECLARE
	 			            next_correlative BIGINT;
	 			        BEGIN
	 			            -- Obtener el siguiente correlativo para el sensor_api_key
	 			            SELECT COALESCE(MAX(sensor_correlative), 0) + 1
	 			            INTO next_correlative
	 			            FROM sensor_data
	 			            WHERE sensor_api_key = NEW.sensor_api_key;
	 		
	 			            -- Asignar el nuevo correlativo
	 			            NEW.sensor_correlative := next_correlative;
	 		
	 			            RETURN NEW;
	 			        END;
	 			        $$ LANGUAGE plpgsql;	
	 			        """);	        
	        	
	        }
	       
	        if (!doesTriggerExist("trg_generate_correlative")) {
	            jdbcTemplate.execute("""
	                    CREATE TRIGGER trg_generate_correlative
	                    BEFORE INSERT ON sensor_data
	                    FOR EACH ROW
	                    EXECUTE FUNCTION generate_sensor_correlative();
	                """);      
	        }
	        
	    }
	    public boolean doesExistAnyRecord(String tableName) {	
	    	try {
	    		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class) > 0;	    		
	    	} catch (Exception e) {	            
	            System.err.println("Error al verificar existencia de registros en la tabla " + tableName + ": " + e.getMessage());
	            return false;
	        }
	    }
	    
	    // Método para verificar si la función existe
	    private boolean doesFunctionExist(String functionName) {
	        String query = "SELECT COUNT(*) FROM pg_proc WHERE proname = ?";
	        Integer count = jdbcTemplate.queryForObject(query, Integer.class, functionName);
	        return count != null && count > 0;
	    }

	    // Método para verificar si el trigger existe
	    private boolean doesTriggerExist(String triggerName) {
	        String query = "SELECT COUNT(*) FROM pg_trigger WHERE tgname = ?";
	        Integer count = jdbcTemplate.queryForObject(query, Integer.class, triggerName);
	        return count != null && count > 0;
	    }

}
