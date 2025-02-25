package com.sbproject.gameplatform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbproject.gameplatform.TestDataUtil;
import com.sbproject.gameplatform.domain.entities.CompanyEntity;
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
                MockMvcResultMatchers.jsonPath("$.title").value("Dungeonmania")
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
        GameEntity savedEntity = gameService.createGame(testGameEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/games")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()
        ).andExpect(content().string(containsString("{\"" +
                "id\":"+savedEntity.getId()+
                ",\"title\":\""+savedEntity.getTitle()+"\"" +
                ",\"company\":"+savedEntity.getCompany()+
                "}")));
    }
}
