package org.example.domain.player.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.game.infrastructure.GamePlayRepository;
import org.example.domain.player.infrastructure.PlayerRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

public class ControllerTest {

    @MockBean
    PlayerRepository playerRepository;

    @MockBean
    GamePlayRepository gamePlayRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
