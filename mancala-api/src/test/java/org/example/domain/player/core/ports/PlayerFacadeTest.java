package org.example.domain.player.core.ports;

import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.command.GetPlayerCommand;
import org.example.domain.player.core.model.exceptions.PlayerNotFoundException;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PlayerFacadeTest {

    @Mock
    private PlayerDatabase playerDatabase;


    @InjectMocks
    private PlayerFacade playerFacade ;


    @Test
    public void givenValidAddPlayerCommandSuccessfullyCreatePlayer() {
        final AddPlayerCommand addPlayerCommand = new AddPlayerCommand("temp", "Something");
        final String mockedId = "SomeValue";
        when(playerDatabase.save(any(Player.class))).thenReturn(new PlayerIdentifier(mockedId));
        assertEquals(mockedId, playerFacade.add(addPlayerCommand).getPlayerId());

    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidAddPlayerCommandGivesError() {
        final AddPlayerCommand addPlayerCommand = new AddPlayerCommand();
        playerFacade.add(addPlayerCommand);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidAddPlayerCommandWithEmptyStringGivesError() {
        final AddPlayerCommand addPlayerCommand = new AddPlayerCommand("","");
        playerFacade.add(addPlayerCommand);
    }

    @Test
    public void givenValidGetPlayerCommandSuccessfullyGetsPlayer() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final GetPlayerCommand getPlayerCommand = mock(GetPlayerCommand.class);
        when(getPlayerCommand.getPlayerId()).thenReturn("Something");
        when(playerDatabase
                .getExistingPlayer(any(String.class)))
                .thenReturn(Optional.of(new PlayerResponse(firstName, lastName)));
        assertEquals(playerFacade.get(getPlayerCommand).getFirstName(), firstName);
        assertEquals(playerFacade.get(getPlayerCommand).getLastName(), lastName);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void givenInValidGetPlayerCommandGivesError() {

        final GetPlayerCommand getPlayerCommand = mock(GetPlayerCommand.class);
        when(getPlayerCommand.getPlayerId()).thenReturn("Something");
        when(playerDatabase
                .getExistingPlayer(any(String.class)))
                .thenReturn(Optional.empty());
        playerFacade.get(getPlayerCommand);
    }

}