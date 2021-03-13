package org.example.domain.game.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public class PlayerState implements Serializable {

    private final String playerId;
    @Setter
    private boolean myTurn;
    private final PlayerSide playerSide;

}
