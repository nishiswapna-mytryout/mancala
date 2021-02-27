package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class Pit {
    @NonNull
    private final String pitPosition;
    private int currentStoneCount;

    public int put(int incomingStoneCount) {
        if (incomingStoneCount<0){
            throw new IllegalArgumentException("Incoming stone count cannot be negative");
        }
        return currentStoneCount += incomingStoneCount;
    }

    public int emptyPit(){

        if(this.currentStoneCount==0){
            throw new IllegalArgumentException("Pit is already empty, pick another pit");
        }

        int pickedUpStones = this.currentStoneCount;
        this.currentStoneCount = 0;
        return pickedUpStones;

    }

}
