package com.sbproject.gameplatform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbproject.gameplatform.TestDataUtil;
import com.sbproject.gameplatform.domain.dto.GameDTO;
import com.sbproject.gameplatform.domain.entities.GameEntity;
import com.sbproject.gameplatform.services.GameService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {

    private GameService gameService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public GameControllerIntegrationTest(GameService gameService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.gameService = gameService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @Transactional
    public void testThatCreateGameReturnsHttpStatus201Created() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        String gameJson = objectMapper.writeValueAsString(testGameA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatCreateGameReturnsSavedGame() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        String gameJson = objectMapper.writeValueAsString(testGameA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testGameA.getTitle())
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatListGamesReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/games")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatListGamesReturnsSavedGames() throws Exception {
        GameEntity testGameEntityA = TestDataUtil.createTestGameA(null);
        testGameEntityA.setId(null);
        testGameEntityA.setTitle("AAAAAAAAAAAA" + testGameEntityA.getTitle());
        GameEntity savedEntity = gameService.save(testGameEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games?sort=title")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()
        ).andExpect(content().string(containsString("{\"" +
                "id\":"+savedEntity.getId()+
                ",\"title\":\""+savedEntity.getTitle()+"\"" +
                ",\"company\":"+savedEntity.getCompany()+
                "}")));
    }

    @Test
    @Transactional
    public void testThatGetGameReturnsHttp200WhenExists() throws Exception {
        GameEntity testGameEntityA = TestDataUtil.createTestGameA(null);
        testGameEntityA.setId(null);
        GameEntity savedGame = gameService.save(testGameEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatGetGameReturnsHttp404WhenDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/882835")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatGetGameReturnsGameWhenExists() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedGame.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedGame.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.company").value(savedGame.getCompany())
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatFullUpdateGameReturnsHttp404WhenDoesNotExist() throws Exception {
        GameDTO testGameDTO = TestDataUtil.createTestGameDTO_A(null);
        String gameJson = objectMapper.writeValueAsString(testGameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/games/882835")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatFullUpdateGameReturnsHttp200WhenExists() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        GameDTO testGameDTO = TestDataUtil.createTestGameDTO_A(null);
        String gameJson = objectMapper.writeValueAsString(testGameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatFullUpdateGameUpdatesExistingGame() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        GameEntity testGameB = TestDataUtil.createTestGameB(null);
        testGameB.setId(savedGame.getId());

        String updatedGameJson = objectMapper.writeValueAsString(testGameB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedGameJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedGame.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testGameB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.company").value(testGameB.getCompany())
        ).andDo(print());

    }

    @Test
    @Transactional
    public void testThatPartialUpdateGameReturnsHttp200WhenExists() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        GameDTO testGameDTO = TestDataUtil.createTestGameDTO_A(null);
        testGameDTO.setTitle(testGameDTO.getTitle() + " 2");
        String gameJson = objectMapper.writeValueAsString(testGameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatPartialUpdateGameUpdatesExistingGame() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        GameEntity testGameB = TestDataUtil.createTestGameB(null);
        testGameB.setTitle(testGameB.getTitle() + " 2");
        testGameB.setId(savedGame.getId());

        String updatedGameJson = objectMapper.writeValueAsString(testGameB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedGameJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedGame.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testGameB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.company").value(testGameB.getCompany())
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatDeleteGameReturnsHttp204OnExistingGame() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatDeleteGameReturnsHttp204OnNotExistingGame() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/games/73457")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        ).andDo(print());
    }

    @Test
    @Transactional
    public void testThatDeleteGameDeletesExistingGame() throws Exception {
        GameEntity testGameA = TestDataUtil.createTestGameA(null);
        testGameA.setId(null);
        GameEntity savedGame = gameService.save(testGameA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(print());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games/"+savedGame.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andDo(print());
    }

}
