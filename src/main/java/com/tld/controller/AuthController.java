package com.tld.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import com.tld.model.Users;
import com.tld.service.UserService;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") //Permite solicitudes desde cualquier origen (CORS habilitado globalmente).
public class AuthController {
	
	 	@Autowired
	    private AuthenticationManager authenticationManager; // autenticar a los usuarios.


	    @Autowired
	    private UserService userService; //registrar usuarios y gestionar credenciales.
	    
	    
	    //Recibe el username y password en la solicitud.
	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(username, password));

	            // Set the session
	            SecurityContextHolder.getContext().setAuthentication(authentication); //Asigna el usuario autenticado
	            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext()); //Se almacena en la sesión

	            return ResponseEntity.ok("Usuario autenticado: " + username);
	        } catch (AuthenticationException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
	        }
	    }

	    @PostMapping("/register")
	    public String register(@RequestBody Users user) {
	        if (userService.findByUsername(user.getUserName()).isPresent()) {
	            return "El nombre de usuario ya está en uso.";
	        }
	        userService.registerUser(user);
	        return "Usuario registrado con éxito.";
	    }

//	    @GetMapping("/user")
//	    public String user() {
//	        try {
//	            return "Usuario autenticado: ";
//	        } catch (AuthenticationException e) {
//	            return "Error de autenticación: " + e.getMessage();
//	        }
//	    }
	    
	    @GetMapping("/user")
	    public ResponseEntity<String> user() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // para devolver el usuario autenticado.
	        if (authentication != null && authentication.isAuthenticated()) {
	            return ResponseEntity.ok("Usuario autenticado: " + authentication.getName());
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay usuario autenticado.");
	        }
	    }

	    @PostMapping("/change-password")
	    public String changePassword(@RequestParam String username, @RequestParam String newPassword) {
	        Optional<Users> user = userService.findByUsername(username);
	        if (user.isPresent()) {
	            userService.updatePassword(user.get(), newPassword); //Si el usuario existe, actualiza su contraseña y si no debe retornar una excepción
	            return "Contraseña actualizada con éxito.";
	        } else {
	            return "Usuario no encontrado.";
	        }
	    }

	
}
