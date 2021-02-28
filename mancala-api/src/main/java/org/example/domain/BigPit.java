package org.example.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BigPit implements Pit{
    @NonNull
    private final String pitPosition;
    private int currentStoneCount = 0;

    @Override
    public int sow(int incomingStoneCount) {
        return currentStoneCount += incomingStoneCount;
    }

    @Override
    public boolean equals(Object pit) {
        return pit instanceof BigPit && this.getPitPosition().equals(((BigPit) pit).getPitPosition());
    }
}
