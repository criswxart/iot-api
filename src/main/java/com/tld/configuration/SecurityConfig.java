package com.tld.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.tld.configuration.jwt.JwtAuthenticationFilter;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@Configuration //Indica que esta clase proporciona configuraciones a la aplicación
@EnableWebSecurity // Habilita la seguridad en la aplicación con Spring Security.
public class SecurityConfig {
	
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 
	 public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }
	 
	 @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (opcional)
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/auth/login", "/api/v1/sensordata/**", "/api/v1/sensor/**","/api/v1/rabbit/**","/api/v1/measurement/**").permitAll() // Endpoints públicos
	                .requestMatchers("/api/location", "/api/auth/register").hasRole("ADMINISTRADOR") // Solo administradores
	                .anyRequest().authenticated() // Todo lo demás requiere autenticación
	            )
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agrega filtro JWT

	        return http.build();
	    }
	
	//SecurityFilterChain
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(authorizeHttpRequests -> 
//			authorizeHttpRequests //Aqui se definen las reglas de acceso a las rutas.
//					.requestMatchers("/api/auth/login","/api/sensordata","/api/sensor").permitAll()//Aca se definen los end point que no necesitan validacion de usuario
//					.requestMatchers("/api/location","/api/auth/register").hasRole("administrador") //Aca definen las rutas restringidas, en este caso solo para usuarios con rol administrador
//				.anyRequest().authenticated() //Cualquier otra solicitud requiere autenticación.
//			)
//		// Deshabilita la protección CSRF en las rutas específicas (/login, /register, /change-password, /logout)
//		//.csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/change-password",  "/logout", "/api/location/add"))
//		.csrf(csrf -> csrf.disable())
//		.formLogin(form -> form // Configura el inicio de sesión mediante formulario.
//				.successHandler(customAthenticationSuccessHandler()) //Usa un manejador de éxito personalizado cuando la autenticación es exitosa.
//		)
//        .httpBasic(withDefaults()); //se activa la autenticación HTTP Basic.
//
//		return http.build();
//	}
    
//	@Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeHttpRequests -> 
//        authorizeHttpRequests
//                 // Permite todas las solicitudes sin 
//        		.requestMatchers("/api/auth/login","/api/sensordata/add").permitAll()
//        		.requestMatchers("/api/sensor/add","/api/auth/register","/api/location/add").hasRole("administrador")
//                .anyRequest().authenticated()
//
//            )
//            .csrf(csrf -> csrf.disable()) // Deshabilita protección CSRF (opcional, dependiendo de tu caso)
//            .formLogin(form -> form.disable()) // Deshabilita formulario de login
//            .httpBasic(httpBasic -> httpBasic.disable()); // Deshabilita autenticación básica
//
//        return http.build();
//    }
    
    //customAthenticationSuccessHandler / AuthenticationSuccessHandler personalizado.
    //Cuando el usuario se autentica correctamente, simplemente se devuelve un HTTP 200 OK
    @Bean
    AuthenticationSuccessHandler customAthenticationSuccessHandler() {
    	return new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				response.setStatus(HttpServletResponse.SC_OK);
			}
		};
    }
    
    //AuthenticationManager el cual se obtiene de la configuracion de autenticacion
    //Es responsable de manejar la autenticación de usuarios.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

  //PasswordEncoder
  //Cuando un usuario se registra, su contraseña se almacena de forma segura en la base de datos con BCrypt.
  @Bean
  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //BCryptPasswordEncoder para codificar y comparar contraseñas de forma segura.
	}
  
}
