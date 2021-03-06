package org.example.domain.game.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.Pit;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class PlayerState implements Serializable {

    private final String playerId;
    private final boolean playerTurn;
    private final List<Pit> pits;

}
