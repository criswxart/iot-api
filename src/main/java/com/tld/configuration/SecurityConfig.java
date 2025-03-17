package com.tld.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	/*Para evitar validaciones*/
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permite todo
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para permitir POST sin token
            .formLogin(login -> login.disable()) // Desactiva el formulario de login
            .httpBasic(basic -> basic.disable()); // Desactiva autenticación básica
        return http.build();
    }
	
	
}
