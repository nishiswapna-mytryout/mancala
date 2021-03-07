package org.example.domain.game.core.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerScore {
    private final String playerId;
    private final int bigPitStoneCount;
}
