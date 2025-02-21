package com.sbproject.gameplatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbproject.gameplatform.TestDataUtil;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CompanyControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public CompanyControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @Transactional
    public void testThatCreateCompanyReturnsHttp201Created() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        String companyJson = objectMapper.writeValueAsString(testCompanyA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatCreateCompanyReturnsSavedCompany() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        String companyJson = objectMapper.writeValueAsString(testCompanyA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Mambo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearFounded").value(1980)
        ).andDo(print());
    }

}
