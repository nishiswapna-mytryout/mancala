package org.example.domain.player.core.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetPlayerCommand {
    private final String playerId;
}
