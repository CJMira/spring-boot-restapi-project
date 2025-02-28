package com.sbproject.gameplatform.services;

import com.sbproject.gameplatform.domain.entities.CompanyEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    CompanyEntity save(CompanyEntity companyEntity);

    List<CompanyEntity> findAll();

    Optional<CompanyEntity> findOne(Long id);

    boolean isExists(Long id);

    CompanyEntity partialUpdate(Long id, CompanyEntity companyEntity);

    void delete(Long id);
}
