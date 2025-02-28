package com.sbproject.gameplatform.services.impl;

import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.repositories.GameRepository;
import com.sbproject.gameplatform.services.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameEntity save(GameEntity gameEntity) {
        return gameRepository.save(gameEntity);
    }

    @Override
    public List<GameEntity> findAll() {
        return StreamSupport.stream(
                        gameRepository
                                .findAll()
                                .spliterator()
                        ,false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<GameEntity> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    public Optional<GameEntity> findOne(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return gameRepository.existsById(id);
    }

    @Override
    public GameEntity partialUpdate(Long id, GameEntity gameEntity) {
        gameEntity.setId(id);

        return gameRepository.findById(id).map(existingGame ->{
            Optional.ofNullable(gameEntity.getTitle()).ifPresent(existingGame::setTitle);
            return gameRepository.save(existingGame);
        }).orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

    @Override
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }
}
