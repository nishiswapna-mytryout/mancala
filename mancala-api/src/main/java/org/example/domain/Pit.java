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

    public int sow(int incomingStoneCount) {
        return currentStoneCount += incomingStoneCount;
    }


}
