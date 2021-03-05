package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class PlayerState {

    @NonNull
    private final UUID playerID;
    @NonNull
    private final boolean playerTurn;
    @NonNull
    private final List<Pit> pitState;


}
