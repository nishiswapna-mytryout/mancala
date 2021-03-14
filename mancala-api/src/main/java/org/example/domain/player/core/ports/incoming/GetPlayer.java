package org.example.domain.player.core.ports.incoming;

import org.example.domain.player.core.model.command.GetPlayerCommand;
import org.example.domain.player.core.model.output.PlayerResponse;

public interface GetPlayer {
    PlayerResponse get(final GetPlayerCommand getPlayerCommand);
}
