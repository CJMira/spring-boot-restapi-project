package com.sbproject.gameplatform.services;

import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    CompanyEntity save(CompanyEntity companyEntity);

    List<CompanyEntity> findAll();

    Page<CompanyEntity> findAll(Pageable pageable);

    Optional<CompanyEntity> findOne(Long id);

    boolean isExists(Long id);

    CompanyEntity partialUpdate(Long id, CompanyEntity companyEntity);

    void delete(Long id);
}
