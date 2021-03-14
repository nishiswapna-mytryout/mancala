package org.example.domain.game.core.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SowCommand {
    private final String gameId;
    private final String pickPosition;
    private final String movingPlayerId;
}
