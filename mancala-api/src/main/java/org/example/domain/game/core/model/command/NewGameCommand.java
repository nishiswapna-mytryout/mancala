package org.example.domain.game.core.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewGameCommand {

    private String playerIdA;
    private String playerIdB;
}
