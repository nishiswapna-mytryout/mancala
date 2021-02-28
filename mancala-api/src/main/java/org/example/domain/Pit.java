package org.example.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class Pit {

    private final String pitPosition;
    @Setter(AccessLevel.PROTECTED)
    private int currentStoneCount;

    public int sow(int incomingStoneCount) {
        if (incomingStoneCount < 0) {
            throw new IllegalArgumentException("Incoming stone count cannot be negative");
        }
        return currentStoneCount += incomingStoneCount;
    }

    public boolean isEmpty() {
        return currentStoneCount == 0;
    }

}
