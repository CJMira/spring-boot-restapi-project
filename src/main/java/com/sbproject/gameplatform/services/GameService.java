package com.sbproject.gameplatform.services;

import com.sbproject.gameplatform.domain.entities.GameEntity;

import java.util.List;

public interface GameService {

    GameEntity createGame(GameEntity gameEntity);

    List<GameEntity> findAll();
}
