package com.sbproject.gameplatform.mappers.impl;

import com.sbproject.gameplatform.domain.dto.CompanyDTO;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements Mapper<CompanyEntity, CompanyDTO> {

    private ModelMapper modelMapper;

    public CompanyMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CompanyDTO mapTo(CompanyEntity companyEntity) {
        return modelMapper.map(companyEntity, CompanyDTO.class);
    }

    @Override
    public CompanyEntity mapFrom(CompanyDTO companyDTO) {
        return modelMapper.map(companyDTO, CompanyEntity.class);
    }
}
