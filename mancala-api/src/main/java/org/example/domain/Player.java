package org.example.domain;

import lombok.Getter;
import lombok.NonNull;

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
    public TreeSet<Pit> getPits() {
        return new TreeSet<>(this.pits);
    }

    public Pit getPit(String pitPosition){
        return pits.stream()
                .filter(pit->pit.getPitPosition().equals(pitPosition))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Invalid Pit Position"));
    }

    public int pickStones(Pit fromPit) {
        if(fromPit==null){
            throw new IllegalArgumentException("Pit cannot be null");
        }
        return fromPit.pick();
    }

    public boolean areAllPitsEmpty(){
        return this.getPits().stream().allMatch(pit->pit.getCurrentStoneCount()==0);
    }

    public int emptyAllPits(){
        return this.getPits().stream()
                .map(Pit::pick)
                .reduce(0,Integer::sum);
    }


    public void sowStonesInBigPit(int stoneCount) {

        if (stoneCount > 0) {
            bigPit.put(stoneCount);
        }

    }
}
