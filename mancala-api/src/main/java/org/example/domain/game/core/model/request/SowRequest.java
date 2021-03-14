package org.example.domain.game.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SowRequest {
    private final String pickPosition;
    private final String movingPlayerId;
}
