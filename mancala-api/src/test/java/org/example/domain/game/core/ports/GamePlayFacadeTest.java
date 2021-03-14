package org.example.domain.game.core.ports;

import org.example.domain.GameBoardFeatures;
import org.example.domain.game.core.model.BigPit;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.SmallPit;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.output.ActiveGameStateResponse;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;
import org.example.domain.player.core.model.exceptions.PlayerNotFoundException;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GamePlayFacadeTest {

    @Mock
    private GamePlayDatabase gamePlayDatabase;

    @Mock
    private PlayerDatabase playerDatabase;

    @Spy
    private final GameBoardFeatures gameBoardFeatures = new GameBoardFeatures(6);


    @InjectMocks
    private GamePlayFacade gamePlayFacade;

    @Test
    public void givenValidPlayersInitializeGame() {

        when(playerDatabase.getExistingPlayer(any(String.class))).thenReturn(Optional.of(new PlayerResponse("Some", "Value")));
        when(gamePlayDatabase.save(any(GameState.class))).thenReturn(GameState.initialize("Play1", "Play2", 6));

        NewGameCommand newGameCommand = new NewGameCommand("Play1", "Play2");
        ActiveGameStateResponse activeGameStateResponse = gamePlayFacade.initialize(newGameCommand);
        assertNotNull(activeGameStateResponse);
        assertTrue(Arrays.asList(activeGameStateResponse
                .getPlayerIdTurn(), activeGameStateResponse.getPlayerIdOpponent())
                .containsAll(Arrays.asList(newGameCommand.getPlayerIdA(), newGameCommand.getPlayerIdB())));
        assertTrue(activeGameStateResponse.getPitState().stream().filter(pit -> pit instanceof SmallPit).allMatch(pit -> pit.getCurrentStoneCount() == 6));
        assertTrue(activeGameStateResponse.getPitState().stream().filter(pit -> pit instanceof BigPit).allMatch(pit -> pit.getCurrentStoneCount() == 0));

    }

    @Test(expected = PlayerNotFoundException.class)
    public void givenInValidPlayersInitializeGame() {

        when(playerDatabase.getExistingPlayer(any(String.class))).thenReturn(Optional.empty());

        NewGameCommand newGameCommand = new NewGameCommand("Play1", "Play2");
        gamePlayFacade.initialize(newGameCommand);

    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidPlayersInitializeGameError() {

        when(playerDatabase.getExistingPlayer(any(String.class))).thenReturn(Optional.of(new PlayerResponse("Some", "Value")));
        when(gamePlayDatabase.save(any(GameState.class))).thenReturn(null);

        NewGameCommand newGameCommand = new NewGameCommand("Play1", "Play2");
        gamePlayFacade.initialize(newGameCommand);

    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidPlayersInitializeGameErrorException() {

        when(playerDatabase.getExistingPlayer(any(String.class))).thenReturn(Optional.of(new PlayerResponse("Some", "Value")));
        when(gamePlayDatabase.save(any(GameState.class))).thenThrow(new IllegalArgumentException());

        NewGameCommand newGameCommand = new NewGameCommand("Play1", "Play2");
        gamePlayFacade.initialize(newGameCommand);

    }

}