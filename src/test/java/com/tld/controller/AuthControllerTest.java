package com.tld.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tld.configuration.jwt.JwtUtils;
import com.tld.model.Users;
import com.tld.service.UserService;
import java.util.Optional;

public class AuthControllerTest {
	
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private UserService userService;
    
    @Mock
    private JwtUtils jwtUtils;
    
    @InjectMocks
    private AuthController authController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
    
 
    
    @Test
    void testLogin_Failure() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Invalid credentials") {});
        
        mockMvc.perform(post("/api/auth/login")
                .param("username", "wrongUser")
                .param("password", "wrongPass"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Error de autenticación: Invalid credentials"));
    }
    
 
    
    @Test
    void testGetUser_Success() throws Exception {
        String token = "Bearer validToken";
        String username = "testUser";
        when(jwtUtils.getUsernameFromToken("validToken")).thenReturn(username);
        when(jwtUtils.getAuthoritiesFromToken("validToken")).thenReturn("ROLE_ADMINISTRADOR");

        mockMvc.perform(get("/api/auth/user")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario autenticado: testUser"));
    }
    
    @Test
    void testChangePassword_Success() throws Exception {
        String token = "Bearer validToken";
        String username = "testUser";
        Users user = new Users();
        user.setUserName(username);
        
        when(jwtUtils.getUsernameFromToken("validToken")).thenReturn(username);
        when(userService.findByUsername(username)).thenReturn(Optional.of(user));
        doNothing().when(userService).updatePassword(any(), any());
        
        mockMvc.perform(post("/api/auth/change-password")
                .param("username", username)
                .param("newPassword", "newPass")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("Contraseña actualizada con éxito."));
    }

}
