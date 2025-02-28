package com.sbproject.gameplatform.services;

import com.sbproject.gameplatform.domain.entities.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameService {

    GameEntity save(GameEntity gameEntity);

    List<GameEntity> findAll();

    Optional<GameEntity> findOne(Long id);

    boolean isExists(Long id);

    GameEntity partialUpdate(Long id, GameEntity gameEntity);

    void delete(Long id);
}
