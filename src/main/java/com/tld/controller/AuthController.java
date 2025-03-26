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

import com.tld.configuration.jwt.JwtUtils;
import com.tld.model.Users;
import com.tld.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") //Permite solicitudes desde cualquier origen (CORS habilitado globalmente).
public class AuthController {
	
	 	@Autowired
	    private AuthenticationManager authenticationManager; // autenticar a los usuarios.


	    @Autowired
	    private UserService userService; //registrar usuarios y gestionar credenciales.
	    
	    @Autowired
	    private JwtUtils jwtUtils; // Para generar y validar tokens JWT.
	    
	    
	    @GetMapping("/{id}")
		@Operation(summary="obtener un libro por Id")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "se encontro un libro", content = @Content(mediaType="aplication/json")),
				@ApiResponse(responseCode = "404", description = "no se encontro un libro"),
				@ApiResponse(responseCode = "500", description = "error en el servidor"),
		})
	    // Recibe el username y password y devuelve un JWT en la respuesta.
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(username, password));

	            SecurityContextHolder.getContext().setAuthentication(authentication); // Asigna el usuario autenticado

	            // Generar el token JWT
	            String token = jwtUtils.createToken(authentication);

	            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
	        } catch (AuthenticationException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
	        }
	    }

//	    @PostMapping("/register")
//	    public ResponseEntity<String> register(@RequestBody Users user) {
//	        if (userService.findByUsername(user.getUserName()).isPresent()) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso.");
//	        }
//	        userService.registerUser(user);
//	        return ResponseEntity.ok("Usuario registrado con éxito.");
//	    }
	    @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody Users user, @RequestHeader("Authorization") String token) {
	        try {
	            // Verificar que el token es válido y extraer el rol
	            if (token == null || !token.startsWith("Bearer ")) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o ausente.");
	            }
	            
	            String jwtToken = token.substring(7); // Eliminar el prefijo "Bearer "
	            String username = jwtUtils.getUsernameFromToken(jwtToken);
	            
	            // Aquí deberías verificar si el usuario tiene el rol adecuado (por ejemplo, "ADMINISTRADOR")
	            Optional<Users> loggedInUser = userService.findByUsername(username);
	            if (loggedInUser.isEmpty() || !loggedInUser.get().getRole().equals("ADMINISTRADOR")) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para registrar usuarios.");
	            }

	            // Verificar si el nombre de usuario ya está en uso
	            if (userService.findByUsername(user.getUserName()).isPresent()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso.");
	            }

	            userService.registerUser(user); // Registrar el nuevo usuario
	            return ResponseEntity.ok("Usuario registrado con éxito.");

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor.");
	        }
	    }

//	    @GetMapping("/user")
//	    public String user() {
//	        try {
//	            return "Usuario autenticado: ";
//	        } catch (AuthenticationException e) {
//	            return "Error de autenticación: " + e.getMessage();
//	        }
//	    }
	    
//	    @GetMapping("/user")
//	    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
//	        if (token == null || !token.startsWith("Bearer ")) {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o ausente.");
//	        }
//
//	        token = token.substring(7); // Quitar el prefijo "Bearer "
//	        
//	        try {
//	            String username = jwtUtils.getUsernameFromToken(token);
//	            return ResponseEntity.ok("Usuario autenticado: " + username);
//	        } catch (Exception e) {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
//	        }
//	    }
	    @GetMapping("/user")
	    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
	        if (token == null || !token.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o ausente.");
	        }

	        token = token.substring(7); // Quitar el prefijo "Bearer "
	        
	        try {
	            String username = jwtUtils.getUsernameFromToken(token);
	            String authorities = jwtUtils.getAuthoritiesFromToken(token);  // Aquí obtienes los roles

	            
	            if (authorities.contains("ROLE_ADMINISTRADOR")) {
	                return ResponseEntity.ok("Usuario autenticado: " + username);
	            } else {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para acceder.");
	            }

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
	        }
	    }

//	    @PostMapping("/change-password")
//	    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String newPassword) {
//	        Optional<Users> user = userService.findByUsername(username);
//	        if (user.isPresent()) {
//	            userService.updatePassword(user.get(), newPassword);
//	            return ResponseEntity.ok("Contraseña actualizada con éxito.");
//	        } else {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
//	        }
//	    }
	    
	    @PostMapping("/change-password")
	    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestParam String newPassword, @RequestHeader("Authorization") String token) {
	        if (token == null || !token.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o ausente.");
	        }

	        token = token.substring(7); // Quitar el prefijo "Bearer "

	        try {
	            String loggedInUsername = jwtUtils.getUsernameFromToken(token);  // Obtener el username desde el token
	            if (!username.equals(loggedInUsername)) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes cambiar la contraseña de otro usuario.");
	            }

	            Optional<Users> user = userService.findByUsername(username);
	            if (user.isPresent()) {
	                userService.updatePassword(user.get(), newPassword);
	                return ResponseEntity.ok("Contraseña actualizada con éxito.");
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado.");
	        }
	    }

	
}
