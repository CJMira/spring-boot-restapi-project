package com.sbproject.gameplatform.controllers;

import com.sbproject.gameplatform.domain.dto.CompanyDTO;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import com.sbproject.gameplatform.services.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        CompanyEntity savedCompanyEntity = companyService.save(companyEntity);

        return new ResponseEntity<>(companyMapper.mapTo(savedCompanyEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/companies")
    public Page<CompanyDTO> listCompanies(Pageable pageable){
        Page<CompanyEntity> companies = companyService.findAll(pageable);
        return companies.map(companyMapper::mapTo);
    }

    @GetMapping(path = "/companies/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable("id") Long id){
        Optional<CompanyEntity> foundCompany = companyService.findOne(id);
        return foundCompany.map(companyEntity -> {
            CompanyDTO companyDTO = companyMapper.mapTo(companyEntity);
            return new ResponseEntity<>(companyDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "companies/{id}")
    public ResponseEntity<CompanyDTO> fullUpdateCompany(@PathVariable("id") Long id, @RequestBody CompanyDTO companyDTO){
        if(!companyService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        companyDTO.setId(id);
        CompanyEntity companyEntity = companyMapper.mapFrom(companyDTO);
        CompanyEntity savedCompany = companyService.save(companyEntity);
        return new ResponseEntity<>(
                companyMapper.mapTo(savedCompany),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/companies/{id}")
    public ResponseEntity<CompanyDTO> partialUpdate(@PathVariable("id") Long id, @RequestBody CompanyDTO companyDTO){
        if(!companyService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CompanyEntity companyEntity = companyMapper.mapFrom(companyDTO);
        CompanyEntity updatedCompany = companyService.partialUpdate(id, companyEntity);
        return new ResponseEntity<>(
                companyMapper.mapTo(updatedCompany),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/companies/{id}")
    public ResponseEntity deleteCompany(@PathVariable("id") Long id){
        companyService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
