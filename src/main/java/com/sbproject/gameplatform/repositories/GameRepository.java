package com.sbproject.gameplatform.repositories;

import com.sbproject.gameplatform.domain.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameEntity, Long> {
}
