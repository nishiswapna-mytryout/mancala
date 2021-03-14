package org.example.domain.game.core.model.output;

import lombok.*;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.Pit;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ActiveGameStateResponse {

    private final String gameId;
    private final List<Pit> pitState;
    private final String playerIdTurn;
    private final String playerIdOpponent;

    public static ActiveGameStateResponse from(@NonNull final GameState gameState){

        return new ActiveGameStateResponse(gameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , gameState.getMovingPlayerId()
                , gameState.getOpponentPlayerId());

    }


}
