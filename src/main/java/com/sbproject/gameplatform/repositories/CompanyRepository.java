package com.sbproject.gameplatform.repositories;

import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {
}
