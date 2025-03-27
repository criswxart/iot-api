package com.tld.controller;
import com.tld.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyControllerTest {
	private MockMvc mockMvc;

    @Mock
    private CompanyService companyService;
    
 

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }


    @Test
    void testDeleteCompany_Success() throws Exception {
        Long companyId = 1L;

        when(companyService.deleteCompany(companyId)).thenReturn("Deleted successfully");

        mockMvc.perform(delete("/api/v1/company/{companyId}", companyId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));
    }
}
