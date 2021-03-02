package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ActiveGame {
    private String gameId;
    private List<PlayerState> playerState;
}
