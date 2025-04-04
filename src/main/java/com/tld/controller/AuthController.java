package com.tld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.tld.configuration.jwt.JwtUtils;
import com.tld.entity.Role;
import com.tld.entity.Users;
import com.tld.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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
	    
	    
	    // Recibe el username y password y devuelve un JWT en la respuesta.
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(username, password));

	            SecurityContextHolder.getContext().setAuthentication(authentication); // Asigna el usuario autenticado

	            Users user = userService.findByUsername(username)
	                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

	            // Obtener roles en formato de lista de Strings
	            List<String> roles = user.getRole().stream()
	                    .map(Role::getRoleName) // Asegúrate de que este es el getter correcto
	                    .collect(Collectors.toList());

	            // Generar el token JWT
	            String token = jwtUtils.createToken(authentication);

	            // Crear la respuesta en JSON
	            Map<String, Object> response = new HashMap<>();
	            Map<String, Object> userData = new HashMap<>();
	            
	            userData.put("name", user.getUserName());
	            userData.put("userEnabled", user.isUserEnabled());
	            userData.put("role", roles);

	            response.put("user", userData);
	            response.put("token", token);

	            return ResponseEntity.ok(response);
	         
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

	        token = token.substring(7); // Quitar "Bearer "

	        try {
	            // Obtener el username desde el token
	            String username = jwtUtils.getUsernameFromToken(token);

	            // Buscar usuario en la BD
	            Users user = userService.findByUsername(username)
	                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

	            // Obtener roles en formato de lista de Strings
	            List<String> roles = user.getRole().stream()
	                    .map(Role::getRoleName)
	                    .collect(Collectors.toList());

	            // Crear la respuesta en JSON
	            Map<String, Object> response = new HashMap<>();
	            Map<String, Object> userData = new HashMap<>();
	            
	            userData.put("name", user.getUserName());
	            userData.put("userEnabled", user.isUserEnabled());
	            userData.put("role", roles);

	            response.put("user", userData);

	            return ResponseEntity.ok(response);

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
