package org.example.domain.game.core.ports.incoming;

import org.example.domain.game.core.model.command.GetGameStatusCommand;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.output.ActiveGameStateResponse;
import org.example.domain.game.core.model.output.GameStateResponse;

public interface GamePlay {

    ActiveGameStateResponse initialize(final NewGameCommand newGameCommand);

    ActiveGameStateResponse sow(final SowCommand sowCommand);

    GameStateResponse getStatus(final GetGameStatusCommand getGameStatusCommand);
}
