package org.example.domain.game.core.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewGameCommand {

    private final String playerIdA;
    private final String playerIdB;
}
