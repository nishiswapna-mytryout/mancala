package org.example.domain.player.core.ports;

import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlayerFacadeTest {

    private PlayerDatabase playerDatabase;

    private PlayerFacade playerFacade;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        playerDatabase = mock(PlayerDatabase.class);
        playerFacade = new PlayerFacade(playerDatabase);
    }

    @Test
    public void givenValidAddPlayerCommandSuccessfullyCreatePlayer(){
        final AddPlayerCommand addPlayerCommand = new AddPlayerCommand("temp","Something");
        final String mockedId = "SomeValue";
        when(playerDatabase.save(any(Player.class))).thenReturn(new PlayerIdentifier(mockedId));
        assertEquals(mockedId, playerFacade.add(addPlayerCommand).getPlayerId());

    }

    @Test
    public void givenInvalidAddPlayerCommandGivesError(){
        final AddPlayerCommand addPlayerCommand = new AddPlayerCommand();
        assertThrows(IllegalArgumentException.class, ()->playerFacade.add(addPlayerCommand));

    }

}