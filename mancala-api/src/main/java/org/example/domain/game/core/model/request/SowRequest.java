package org.example.domain.game.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SowRequest {
    private String pickPosition;
    private String movingPlayerId;
}
