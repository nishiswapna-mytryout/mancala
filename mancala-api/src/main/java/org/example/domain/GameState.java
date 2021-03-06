package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameState {
    @NonNull
    private final String gameId;
    @NonNull
    private final List<playerstate1> playerstate1;
}
