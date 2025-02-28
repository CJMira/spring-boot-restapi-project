package com.sbproject.gameplatform.services;

import com.sbproject.gameplatform.domain.entities.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GameService {

    GameEntity save(GameEntity gameEntity);

    List<GameEntity> findAll();

    Page<GameEntity> findAll(Pageable pageable);

    Optional<GameEntity> findOne(Long id);

    boolean isExists(Long id);

    GameEntity partialUpdate(Long id, GameEntity gameEntity);

    void delete(Long id);
}
