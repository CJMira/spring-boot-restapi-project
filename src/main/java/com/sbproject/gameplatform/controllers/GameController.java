package com.sbproject.gameplatform.controllers;

import com.sbproject.gameplatform.domain.dto.GameDTO;
import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import com.sbproject.gameplatform.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private Mapper<GameEntity, GameDTO> gameMapper;

    private GameService gameService;

    public GameController(Mapper<GameEntity, GameDTO> gameMapper, GameService gameService) {
        this.gameMapper = gameMapper;
        this.gameService = gameService;
    }

    @PostMapping(path = "/games")
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO){
        GameEntity gameEntity = gameMapper.mapFrom(gameDTO);
        GameEntity savedGameEntity = gameService.createGame(gameEntity);

        return new ResponseEntity<>(gameMapper.mapTo(savedGameEntity), HttpStatus.CREATED);
    }

}
