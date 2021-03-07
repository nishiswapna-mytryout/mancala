package org.example.domain.game.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayerSide {
    SideA ("A"), SideB ("B");

    private final String gameSide;


    @Override
    public String toString() {
        return gameSide;
    }
}
