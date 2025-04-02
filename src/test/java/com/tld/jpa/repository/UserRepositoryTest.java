package com.tld.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tld.entity.Users;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest  // Esta anotación configura una base de datos en memoria y configura los repositorios JPA para pruebas.
public class UserRepositoryTest {

//	@Autowired
//    private UserRepository userRepository;

//    @Test
//    void testFindByUserName_Exists() {
//        Users user = new Users();
//        user.setUserName("testuser");
//        user.setUserPassword("password123");
//
//        userRepository.save(user);
//        Optional<Users> foundUser = userRepository.findByUserName("testuser");
//
//        assertTrue(foundUser.isPresent());
//        assertEquals("testuser", foundUser.get().getUserName());
//    }

//    @Test
//    public void testFindByUserName_NotExists() {
//        // Buscar un usuario que no existe
//        Optional<Users> foundUser = userRepository.findByUserName("nonexistentuser");
//
//        // Verificar que no se encuentra el usuario
//        assertFalse(foundUser.isPresent(), "User should not be found");
//    }

  
}
