package com.tld.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

import java.io.IOException;

@Configuration //Indica que esta clase proporciona configuraciones a la aplicación
@EnableWebSecurity // Habilita la seguridad en la aplicación con Spring Security.
public class SecurityConfig {
	
	//SecurityFilterChain
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeHttpRequests -> 
			authorizeHttpRequests //Aqui se definen las reglas de acceso a las rutas.
					.requestMatchers("/api/auth/login").permitAll() // Permite acceso sin autenticación.
					.requestMatchers("/api/auth/register").hasRole("ADMIN") // Solo accesible por usuarios con el rol ADMIN.
					.anyRequest().authenticated() //Cualquier otra solicitud requiere autenticación.
			)
		// Deshabilita la protección CSRF en las rutas específicas (/login, /register, /change-password, /logout)
		.csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/change-password",  "/logout"))
		.formLogin(form -> form // Configura el inicio de sesión mediante formulario.
				.successHandler(customAthenticationSuccessHandler()) //Usa un manejador de éxito personalizado cuando la autenticación es exitosa.
		)
        .httpBasic(withDefaults()); //se activa la autenticación HTTP Basic.

		return http.build();
	}
    
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
