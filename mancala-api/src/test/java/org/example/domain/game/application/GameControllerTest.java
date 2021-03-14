package org.example.domain.game.application;

import org.assertj.core.util.Arrays;
import org.example.domain.game.core.model.Pit;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.output.ActiveGameStateResponse;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.player.application.ControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
@DirtiesContext
@RunWith(SpringRunner.class)
public class GameControllerTest extends ControllerTest {

    @MockBean
    GamePlay gamePlay;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenTwoValidPlayersGameInitializes() throws Exception {

        List<Pit> pitlist = new ArrayList<>();

        when(gamePlay.initialize(any(NewGameCommand.class)))
                .thenReturn(new ActiveGameStateResponse("SomeGameId",pitlist,"SomePlayerId","SomeOppId"));

        this.mockMvc.perform(post("/game")
                .content(asJsonString(new NewGameCommand("playerIdA","playerIdB")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("SomeGameId"))
                .andExpect(jsonPath("$.playerIdTurn").value("SomePlayerId"))
                .andExpect(jsonPath("$.playerIdOpponent").value("SomeOppId"))
                .andReturn();
    }

    @Test
    public void givenOneUnknownPlayerGameInitializationFailure() throws Exception {
        when(gamePlay.initialize(any(NewGameCommand.class)))
                .thenThrow(new IllegalArgumentException("Invalid player"));

        this.mockMvc.perform(post("/game")
                .content(asJsonString(new NewGameCommand("playerIdA","playerIdB")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorTime").exists())
                .andReturn();
    }

    @Test
    public void givenTwoValidPlayersGameInitializationError() throws Exception{

        when(gamePlay.initialize(any(NewGameCommand.class)))
                .thenReturn(null);

        this.mockMvc.perform(post("/game")
                .content(asJsonString(new NewGameCommand("playerIdA","playerIdB")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    public void givenValidgameIdandsowRequestGameSowSuccess() throws Exception{

        List<Pit> pitlist = new ArrayList<Pit>();
        when(gamePlay.sow(any(SowCommand.class)))
                .thenReturn(new ActiveGameStateResponse("SomeGameId",pitlist,"SomePlayerId","SomeOppId"));

        this.mockMvc.perform(post("/game/{id}")
                .content(asJsonString(new SowCommand("someGameId","somePickPosition","somePlayerId")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId").value("SomeGameId"))
                .andExpect(jsonPath("$.pickPosition").value("somePickPosition"))
                .andExpect(jsonPath("$.movingPlayerId").value("somePlayerId"))
                .andReturn();
    }

    @Test
    public void givenInvalidgameIdandsowRequestGameSowError() throws Exception{

    }

    @Test
    public void givenValidgameIdandsowRequestNoGameSowResponse() throws Exception{

    }


    }