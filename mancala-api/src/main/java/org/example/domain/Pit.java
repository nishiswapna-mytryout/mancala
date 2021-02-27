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
        return currentStoneCount += incomingStoneCount;
    }

    public int emptyPit(){

        int pickedUpStones = this.currentStoneCount;
        this.currentStoneCount = 0;
        return pickedUpStones;

    }

}
