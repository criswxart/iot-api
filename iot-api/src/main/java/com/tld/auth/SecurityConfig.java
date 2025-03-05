package com.tld.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	  @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			return http
					.httpBasic(Customizer.withDefaults())
					.authorizeHttpRequests(httpRequest -> {
						httpRequest.requestMatchers(HttpMethod.GET, "/api/get-public").permitAll();
						
						httpRequest.requestMatchers(HttpMethod.GET, "/api/get-private").hasAnyAuthority("CREATE");
						
						httpRequest.anyRequest().authenticated();
					})
					.build();
		}

	    //AuthenticationManager
	    @Bean
	    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
			return authenticationConfiguration.getAuthenticationManager();
		}
	    
	    @Bean
	    public AuthenticationProvider authenticationProvider() {
	    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    	
	    	provider.setPasswordEncoder(passwordEncoder());
	    	provider.setUserDetailsService(userDetailsService());
	    	
	    	return provider;
	    }

	    //UserDetailsService
	    @Bean
	    UserDetailsService userDetailsService() {
			UserDetails user1 = User.builder()
					.username("user_admin")
					.password("1234")
					.roles("ADMIN")
					.authorities("READ", "CREATE")
					.build();
			
			UserDetails user2 = User.builder()
					.username("user_user")
					.password("12345")
					.roles("USER")
					.authorities("READ")
					.build();
			
			return new InMemoryUserDetailsManager(user1, user2);
		}

	    //PasswordEncoder
	    @Bean
	    PasswordEncoder passwordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}
}