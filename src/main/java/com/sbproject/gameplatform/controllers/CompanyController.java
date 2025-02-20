package com.sbproject.gameplatform.controllers;

import com.sbproject.gameplatform.domain.dto.CompanyDTO;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import com.sbproject.gameplatform.services.CompanyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private CompanyService companyService;

    private Mapper<CompanyEntity, CompanyDTO> companyMapper;

    public CompanyController(CompanyService companyService, Mapper<CompanyEntity, CompanyDTO> companyMapper){
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping(path = "/companies")
    public CompanyDTO createCompany(@RequestBody CompanyDTO company){
        CompanyEntity companyEntity = companyMapper.mapFrom(company);
        CompanyEntity savedCompanyEntity = companyService.createCompany(companyEntity);

        return companyMapper.mapTo(savedCompanyEntity);
    }

}
