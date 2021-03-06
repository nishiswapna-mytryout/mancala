package org.example.domain.game.core.ports.incoming;

import org.example.domain.game.core.model.output.GameStateResponse;

public interface GamePlay {

    GameStateResponse initialize(final String playerIdA, final String playerIdB);
}
