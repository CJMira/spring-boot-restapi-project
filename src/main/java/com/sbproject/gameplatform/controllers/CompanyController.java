package com.sbproject.gameplatform.controllers;

import com.sbproject.gameplatform.domain.dto.CompanyDTO;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import com.sbproject.gameplatform.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompanyController {

    private CompanyService companyService;

    private Mapper<CompanyEntity, CompanyDTO> companyMapper;

    public CompanyController(CompanyService companyService, Mapper<CompanyEntity, CompanyDTO> companyMapper){
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping(path = "/companies")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO company){
        CompanyEntity companyEntity = companyMapper.mapFrom(company);
        CompanyEntity savedCompanyEntity = companyService.createCompany(companyEntity);

        return new ResponseEntity<>(companyMapper.mapTo(savedCompanyEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/companies")
    public List<CompanyDTO> listCompanies(){
        List<CompanyEntity> companies = companyService.findAll();
        return companies.stream()
                .map(companyMapper::mapTo)
                .collect(Collectors.toList());
    }

}
