package com.sbproject.gameplatform.services;

import com.sbproject.gameplatform.domain.entities.CompanyEntity;

import java.util.List;

public interface CompanyService {
    CompanyEntity createCompany(CompanyEntity companyEntity);

    List<CompanyEntity> findAll();
}
