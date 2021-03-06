package org.example.domain.game.core.ports.incoming;

import org.example.domain.GameBoard;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.springframework.web.bind.annotation.PathVariable;

public interface GamePlay {

    GameStateResponse initialize(final String playerIdA, final String playerIdB);
    GameStateResponse sow(final String gameId, final String pitPosition, final String movingPlayer);
}
