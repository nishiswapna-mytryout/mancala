package org.example.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class Player {
    @Getter
    private final UUID playerId;
    @Getter
    private final String playerName;
    private final BigPit bigPit;
    private final TreeSet<Pit> pits;

    public Player(@NonNull String playerName, @NonNull BigPit bigPit, @NonNull TreeSet<Pit> pits) {
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

    public int pickStones(Pit fromPit) {
        if(fromPit==null){
            throw new IllegalArgumentException("Pit cannot be null");
        }
        return fromPit.emptyPit();
    }


    public void sowStonesInBigPit(int stoneCount) {

        if (stoneCount > 0) {
            bigPit.put(stoneCount);
        }

    }
}
