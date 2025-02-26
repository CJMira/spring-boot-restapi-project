package com.sbproject.gameplatform.controllers;

import com.sbproject.gameplatform.domain.dto.CompanyDTO;
import com.sbproject.gameplatform.domain.dto.GameDTO;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.mappers.Mapper;
import com.sbproject.gameplatform.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        GameEntity savedGameEntity = gameService.save(gameEntity);

        return new ResponseEntity<>(gameMapper.mapTo(savedGameEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/games")
    public List<GameDTO> listGames(){
        List<GameEntity> games = gameService.findAll();
        return  games.stream()
                .map(gameMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/games/{id}")
    public ResponseEntity<GameDTO> getGame(@PathVariable("id") Long id){
        Optional<GameEntity> foundGame = gameService.findOne(id);
        return foundGame.map(gameEntity ->{
            GameDTO gameDTO = gameMapper.mapTo(gameEntity);
            return new ResponseEntity<>(gameDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "games/{id}")
    public ResponseEntity<GameDTO> fullUpdateGame(@PathVariable("id") Long id, @RequestBody GameDTO gameDTO){
        if(!gameService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gameDTO.setId(id);
        GameEntity gameEntity = gameMapper.mapFrom(gameDTO);
        GameEntity savedGame = gameService.save(gameEntity);
        return new ResponseEntity<>(
                gameMapper.mapTo(savedGame),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/games/{id}")
    public ResponseEntity<GameDTO> partialUpdate(@PathVariable("id") Long id, @RequestBody GameDTO gameDTO){
        if(!gameService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GameEntity gameEntity = gameMapper.mapFrom(gameDTO);
        GameEntity updatedGame = gameService.partialUpdate(id, gameEntity);
        return new ResponseEntity<>(
                gameMapper.mapTo(updatedGame),
                HttpStatus.OK);
    }

}
