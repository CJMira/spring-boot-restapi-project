package com.sbproject.gameplatform.services.impl;

import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.repositories.CompanyRepository;
import com.sbproject.gameplatform.services.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyEntity save(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }

    @Override
    public List<CompanyEntity> findAll() {
        return StreamSupport.stream(
                        companyRepository
                                .findAll()
                                .spliterator()
                        ,false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CompanyEntity> findOne(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return companyRepository.existsById(id);
    }

    @Override
    public CompanyEntity partialUpdate(Long id, CompanyEntity companyEntity) {
        companyEntity.setId(id);

        return companyRepository.findById(id).map(existingCompany ->{
            Optional.ofNullable(companyEntity.getName()).ifPresent(existingCompany::setName);
            Optional.ofNullable(companyEntity.getYearFounded()).ifPresent(existingCompany::setYearFounded);
            return companyRepository.save(existingCompany);
        }).orElseThrow(() -> new RuntimeException("Company does not exist"));
    }
}
