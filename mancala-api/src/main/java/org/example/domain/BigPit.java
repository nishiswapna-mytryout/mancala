package org.example.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BigPit {
    @NonNull
    private final String pitPosition;
    private int currentStoneCount = 0;

    public int put(int incomingStoneCount) {
        return currentStoneCount += incomingStoneCount;
    }
}
