package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class Pit implements Comparable<Pit>{
    @NonNull
    private final String pitPosition;
    private int currentStoneCount;

    public int put(int incomingStoneCount) {
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
    public int compareTo(Pit pit) {
        return pit.getPitPosition().compareTo(this.getPitPosition());
    }
}
