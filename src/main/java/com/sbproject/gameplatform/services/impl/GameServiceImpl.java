package com.sbproject.gameplatform.services.impl;

import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.repositories.GameRepository;
import com.sbproject.gameplatform.services.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameEntity createGame(GameEntity gameEntity) {
        return gameRepository.save(gameEntity);
    }
}
