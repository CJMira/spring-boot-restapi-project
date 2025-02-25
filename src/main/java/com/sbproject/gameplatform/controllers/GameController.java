package com.sbproject.gameplatform.controllers;

import com.sbproject.gameplatform.domain.dto.GameDTO;
import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import com.sbproject.gameplatform.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/games")
    public List<GameDTO> listGames(){
        List<GameEntity> games = gameService.findAll();
        return  games.stream()
                .map(gameMapper::mapTo)
                .collect(Collectors.toList());
    }

}
