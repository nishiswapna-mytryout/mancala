package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class SmallPit implements Comparable<SmallPit>,Pit{
    @NonNull
    private final String pitPosition;
    private int currentStoneCount;

    @Override
    public int sow(int incomingStoneCount) {
        if (incomingStoneCount<0){
            throw new IllegalArgumentException("Incoming stone count cannot be negative");
        }
        return currentStoneCount += incomingStoneCount;
    }

    public int pick(){

        if(this.currentStoneCount==0){
            throw new IllegalArgumentException("Pit is already empty, pick another pit");
        }

        int pickedUpStones = this.currentStoneCount;
        this.currentStoneCount = 0;
        return pickedUpStones;

    }

    @Override
    public boolean equals(Object pit) {
        return pit instanceof SmallPit && this.getPitPosition().equals(((SmallPit) pit).getPitPosition());
    }

    @Override
    public int compareTo(SmallPit pit) {
        return pit.getPitPosition().compareTo(this.getPitPosition());
    }
}
