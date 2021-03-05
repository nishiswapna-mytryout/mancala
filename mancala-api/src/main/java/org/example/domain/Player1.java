package org.example.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player1 {
    @Getter
    private final UUID playerId;
    @Getter
    private final String playerName;
    private final BigPit bigPit;
    private final List<SmallPit> pits;

    public Player1(@NonNull String playerName, @NonNull BigPit bigPit, @NonNull List<SmallPit> pits) {
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
    public List<SmallPit> getPits() {
        return new ArrayList<>(this.pits);
    }

    public SmallPit getPit(String pitPosition){
        return pits.stream()
                .filter(pit->pit.getPitPosition().equals(pitPosition))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Invalid Pit Position"));
    }

    public int pickStones(SmallPit fromPit) {
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
                .map(SmallPit::pick)
                .reduce(0,Integer::sum);
    }

    public boolean smallPitBelongsToMe(final SmallPit smallPit){

        return smallPit!=null && pits.contains(smallPit);

    }


    public void sowStonesInBigPit(final int stoneCount) {

        if (stoneCount > 0) {
            bigPit.sow(stoneCount);
        }

    }
}
