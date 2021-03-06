package org.example.domain.game.core.ports.incoming;

import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.output.GameStateResponse;

public interface GamePlay {

    GameStateResponse initialize(final NewGameCommand newGameCommand);

    GameStateResponse sow(final SowCommand sowCommand);
}
