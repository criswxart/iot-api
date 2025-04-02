package com.tld.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.tld.entity.Users;
import com.tld.jpa.repository.CompanyRepository;
import com.tld.jpa.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyServiceImplTest {
	
	 @Mock
	    private CompanyRepository companyRepository;

	    @Mock
	    private UserRepository userRepository;

	    @InjectMocks
	    private CompanyServiceImpl companyServiceImpl;

	    @Mock
	    private Users authenticatedUser;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	   
	    @Test
	    void testDeleteCompanyNotFound() {
	        // Arrange
	        Long companyId = 1L;
	        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

	        // Act & Assert
	        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
	            companyServiceImpl.deleteCompany(companyId);
	        });
	        assertEquals("No existe ninguna location con id: " + companyId, exception.getMessage());
	    }

}
