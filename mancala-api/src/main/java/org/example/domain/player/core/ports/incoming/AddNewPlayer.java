package org.example.domain.player.core.ports.incoming;


import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.output.PlayerIdentifier;

public interface AddNewPlayer {
    PlayerIdentifier handleCommand(final AddPlayerCommand addPlayerCommand);
}
