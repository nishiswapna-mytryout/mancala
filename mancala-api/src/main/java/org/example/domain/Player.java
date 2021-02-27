package org.example.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Player {
    @Getter
    private final UUID playerId;
    @Getter
    private final String playerName;
    private final BigPit bigPit;
    private final Set<Pit> pits;

    public Player(@NonNull String playerName, @NonNull BigPit bigPit, @NonNull Set<Pit> pits) {
        this.playerName = playerName;
        this.playerId = UUID.randomUUID();
        this.bigPit = bigPit;
        this.pits = pits;
    }

    //TODO check if we need copy of big pit here
    public BigPit getBigPit() {
        return bigPit;
    }

    //TODO check if we need copy of pits here
    public Set<Pit> getPits() {
        return new HashSet<>(this.pits);
    }
}
