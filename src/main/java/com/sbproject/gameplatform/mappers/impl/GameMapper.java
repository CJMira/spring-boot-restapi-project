package com.sbproject.gameplatform.mappers.impl;

import com.sbproject.gameplatform.domain.dto.GameDTO;
import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GameMapper implements Mapper<GameEntity, GameDTO> {

    private ModelMapper modelMapper;

    public GameMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GameDTO mapTo(GameEntity gameEntity) {
        return modelMapper.map(gameEntity, GameDTO.class);
    }

    @Override
    public GameEntity mapFrom(GameDTO gameDTO) {
        return modelMapper.map(gameDTO, GameEntity.class);
    }
}
