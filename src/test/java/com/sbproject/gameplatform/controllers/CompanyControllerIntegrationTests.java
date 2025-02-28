package com.sbproject.gameplatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbproject.gameplatform.TestDataUtil;
import com.sbproject.gameplatform.domain.dto.CompanyDTO;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.services.CompanyService;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CompanyControllerIntegrationTests {

    private CompanyService companyService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public CompanyControllerIntegrationTests(CompanyService companyService, MockMvc mockMvc) {
        this.companyService = companyService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @Transactional
    public void testThatCreateCompanyReturnsHttp201Created() throws Exception {
        CompanyDTO testCompanyDTO = TestDataUtil.createTestCompanyDTO_A();
        testCompanyDTO.setId(null);
        String companyJson = objectMapper.writeValueAsString(testCompanyDTO);

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
        CompanyDTO testCompanyDTO = TestDataUtil.createTestCompanyDTO_A();
        testCompanyDTO.setId(null);
        String companyJson = objectMapper.writeValueAsString(testCompanyDTO);

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

    @Test
    @Transactional
    public void testThatListCompaniesReturnsHttp200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    //Checks new saved company is included in the list
    public void testThatListCompaniesReturnsSavedCompanies() throws Exception{
        CompanyEntity testCompanyEntityA = TestDataUtil.createTestCompanyA();
        testCompanyEntityA.setName("AAAAAAAAAAAAAAAAA"+testCompanyEntityA.getName());
        testCompanyEntityA.setId(null);
        CompanyEntity savedEntity = companyService.save(testCompanyEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies?sort=name")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()
        ).andExpect(content().string(containsString("{\"" +
                "id\":"+savedEntity.getId()+
                ",\"name\":\""+savedEntity.getName()+"\"" +
                ",\"yearFounded\":"+savedEntity.getYearFounded()+
                "}")));
    }

    @Test
    @Transactional
    public void testThatGetCompanyReturnsHttp200WhenExists() throws Exception {
        CompanyEntity testCompanyEntityA = TestDataUtil.createTestCompanyA();
        testCompanyEntityA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatGetCompanyReturnsHttp404WhenDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/882835")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatGetCompanyReturnsCompanyWhenExists() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCompany.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Mambo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearFounded").value(1980)
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatFullUpdateCompanyReturnsHttp404WhenDoesNotExist() throws Exception {
        CompanyDTO testCompanyDTO = TestDataUtil.createTestCompanyDTO_A();
        String companyJson = objectMapper.writeValueAsString(testCompanyDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/companies/882835")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatFullUpdateCompanyReturnsHttp200WhenExists() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        CompanyDTO testCompanyDTO = TestDataUtil.createTestCompanyDTO_A();
        String companyJson = objectMapper.writeValueAsString(testCompanyDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatFullUpdateCompanyUpdatesExistingCompany() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        CompanyEntity testCompanyB = TestDataUtil.createTestCompanyB();
        testCompanyB.setId(savedCompany.getId());

        String updatedCompanyJson = objectMapper.writeValueAsString(testCompanyB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompanyJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCompany.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testCompanyB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearFounded").value(testCompanyB.getYearFounded())
        ).andDo(print());

    }

    @Test
    @Transactional
    public void testThatPartialUpdateCompanyReturnsHttp200WhenExists() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        CompanyDTO testCompanyDTO = TestDataUtil.createTestCompanyDTO_A();
        testCompanyDTO.setName("New " + testCompanyDTO.getName());
        String companyJson = objectMapper.writeValueAsString(testCompanyDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatPartialUpdateCompanyUpdatesExistingCompany() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        CompanyDTO testCompanyDTO = TestDataUtil.createTestCompanyDTO_A();
        testCompanyDTO.setName("New " + testCompanyDTO.getName());
        String companyJson = objectMapper.writeValueAsString(testCompanyDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCompany.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("New Mambo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearFounded").value(testCompanyDTO.getYearFounded())
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatDeleteCompanyReturnsHttp204OnExistingCompany() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatDeleteCompanyReturnsHttp204OnNotExistingCompany() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/companies/74956")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatDeleteCompanyDeletesExistingCompany() throws Exception {
        CompanyEntity testCompanyA = TestDataUtil.createTestCompanyA();
        testCompanyA.setId(null);
        CompanyEntity savedCompany = companyService.save(testCompanyA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/companies/"+savedCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andDo(print());
    }

}
