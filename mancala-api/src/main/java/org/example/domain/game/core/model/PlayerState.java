package org.example.domain.game.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.Pit;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlayerState {

    private final String playerId;
    private final boolean playerTurn;
    private final List<Pit> pits;

}
