package org.example.domain.game.core.model;

import lombok.*;
import org.example.domain.interfaces.PitBehavior;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class Pit implements PitBehavior {

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
