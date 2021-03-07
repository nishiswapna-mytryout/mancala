package org.example.domain.game.core.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.Pit;

import java.util.List;

@AllArgsConstructor
@Getter
public class ActiveGameStateResponse {

    private final String gameId;
    private final List<Pit> pitState;
    private final String playerIdTurn;
    private final String playerIdOpponent;
}
