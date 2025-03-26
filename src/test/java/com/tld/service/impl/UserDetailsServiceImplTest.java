package com.tld.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.tld.jpa.repository.UserRepository;
import com.tld.model.Permission;
import com.tld.model.Role;
import com.tld.model.Users;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@SpringBootTest
public class UserDetailsServiceImplTest {
	 @Mock
	    private UserRepository userRepository;

	    @InjectMocks
	    private UserDetailsServiceImpl userDetailsService;

	    private Users user;
	    private Role role;
	    private Permission permission;

	    @BeforeEach
	    void setUp() {
	        // Crear permiso
	        permission = new Permission();
	        permission.setPermissionName("Actualizar");

	        // Crear rol y asignarle permisos
	        role = new Role();
	        role.setRoleName("ROLE_administrador");
	        role.setPermissions(Set.of(permission));

	        // Crear usuario con rol
	        user = new Users();
	        user.setUserName("Luis");
	        user.setUserPassword("123456");
	        user.setUserEnabled(true);
	        user.setAccountNonExpired(true);
	        user.setCredentialsNonExpired(true);
	        user.setAccountNonLocked(true);
	        user.setRole(Set.of(role));
	    }

	    @Test
	    void testLoadUserByUsername_UserExists() {
	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertEquals("Luis", userDetails.getUsername());
	        assertEquals("123456", userDetails.getPassword());

	        // Verifica que el usuario tenga los roles y permisos adecuados
	        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_administrador")));
	        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Actualizar")));

	        verify(userRepository).findByUserName("Luis");
	    }

	    @Test
	    void testLoadUserByUsername_UserNotFound() {
	        when(userRepository.findByUserName("inexistente")).thenReturn(Optional.empty());

	        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
	            userDetailsService.loadUserByUsername("inexistente");
	        });

	        assertEquals("Usuario no encontrado: inexistente", exception.getMessage());
	        verify(userRepository).findByUserName("inexistente");
	    }
	    
	    @Test
	    void testLoadUserByUsername_MultipleRolesAndPermissions() {
	        // Crear permisos adicionales
	        Permission writePermission = new Permission();
	        writePermission.setPermissionName("WRITE_PRIVILEGES");

	        // Crear otros roles
	        Role userRole = new Role();
	        userRole.setRoleName("ROLE_USER");
	        userRole.setPermissions(Set.of(writePermission));

	        Role adminRole = new Role();
	        adminRole.setRoleName("ROLE_administrador");  // Nombre correcto del rol
	        adminRole.setPermissions(Set.of(writePermission));

	        // Asignar ambos roles al usuario
	        user.setRole(Set.of(userRole, adminRole));  // Asegúrate de que el usuario tenga estos roles

	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertEquals("Luis", userDetails.getUsername());
	        assertEquals("123456", userDetails.getPassword());

	        // Verifica que el usuario tenga todos los roles y permisos
	        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_administrador")));  // Cambié el nombre del rol aquí
	        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("WRITE_PRIVILEGES"))); // Asegúrate de que este permiso esté asignado correctamente
	        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
	    }
	    
	    @Test
	    void testLoadUserByUsername_UserDisabled() {
	        // Deshabilitar el usuario
	        user.setUserEnabled(false);

	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertFalse(userDetails.isEnabled()); // Verifica que el usuario esté deshabilitado
	    }
	    
	    @Test
	    void testLoadUserByUsername_AccountExpired() {
	        // Expirar la cuenta
	        user.setAccountNonExpired(false);

	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertFalse(userDetails.isAccountNonExpired()); // Verifica que la cuenta esté expirada
	    }
	    
	    @Test
	    void testLoadUserByUsername_CredentialsExpired() {
	        // Expirar las credenciales
	        user.setCredentialsNonExpired(false);

	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertFalse(userDetails.isCredentialsNonExpired()); // Verifica que las credenciales estén expandidas
	    }
	    
	    @Test
	    void testLoadUserByUsername_AccountLocked() {
	        // Bloquear la cuenta
	        user.setAccountNonLocked(false);

	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertFalse(userDetails.isAccountNonLocked()); // Verifica que la cuenta esté bloqueada
	    }
	    
	    @Test
	    void testLoadUserByUsername_NoRolesOrPermissions() {
	        // Crear usuario sin roles ni permisos
	        user.setRole(new HashSet<>());

	        when(userRepository.findByUserName("Luis")).thenReturn(Optional.of(user));

	        UserDetails userDetails = userDetailsService.loadUserByUsername("Luis");

	        assertNotNull(userDetails);
	        assertEquals("Luis", userDetails.getUsername());
	        assertEquals("123456", userDetails.getPassword());

	        // El usuario no debería tener autoridades
	        assertTrue(userDetails.getAuthorities().isEmpty());
	    }
	    @Test
	    void testLoadUserByUsername_UsernameNotFoundException() {
	        // Configurar el repositorio para devolver Optional.empty()
	        when(userRepository.findByUserName("NonExistentUser")).thenReturn(Optional.empty());

	        // Verificar que la excepción se lanza
	        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
	            userDetailsService.loadUserByUsername("NonExistentUser");
	        });

	        assertEquals("Usuario no encontrado: NonExistentUser", exception.getMessage());
	    }
}
