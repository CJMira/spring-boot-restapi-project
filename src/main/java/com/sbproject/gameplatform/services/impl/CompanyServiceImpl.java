package com.sbproject.gameplatform.services.impl;

import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.repositories.CompanyRepository;
import com.sbproject.gameplatform.services.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyEntity createCompany(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }
}
