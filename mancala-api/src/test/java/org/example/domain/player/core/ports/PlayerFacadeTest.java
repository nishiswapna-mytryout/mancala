package org.example.domain.player.core.ports;

import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.command.GetPlayerCommand;
import org.example.domain.player.core.model.exceptions.PlayerNotFoundException;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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

    @Test
    public void givenValidGetPlayerCommandSuccessfullyGetsPlayer(){
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final GetPlayerCommand getPlayerCommand = mock(GetPlayerCommand.class);
        when(getPlayerCommand.getPlayerId()).thenReturn("Something");
        when(playerDatabase
                .getExistingPlayer(any(String.class)))
                .thenReturn(Optional.of(new PlayerResponse(firstName,lastName)));
        assertEquals(playerFacade.get(getPlayerCommand).getFirstName(),firstName);
        assertEquals(playerFacade.get(getPlayerCommand).getLastName(),lastName);
    }

    @Test
    public void givenInValidGetPlayerCommandGivesError(){

        final GetPlayerCommand getPlayerCommand = mock(GetPlayerCommand.class);
        when(getPlayerCommand.getPlayerId()).thenReturn("Something");
        when(playerDatabase
                .getExistingPlayer(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(PlayerNotFoundException.class, ()->playerFacade.get(getPlayerCommand));
    }

}