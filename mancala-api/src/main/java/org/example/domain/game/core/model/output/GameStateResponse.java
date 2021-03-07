package org.example.domain.game.core.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.game.core.model.Pit;

import java.util.List;

@AllArgsConstructor
@Getter
public class GameStateResponse {

    private final String gameId;
    private final List<Pit> pitState;
    private final boolean hasGameEnded;
    private final List<PlayerScore> playerScores ;

}
