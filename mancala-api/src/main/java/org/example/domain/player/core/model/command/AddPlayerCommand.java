package org.example.domain.player.core.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AddPlayerCommand {

    private String firstName;
    private String lastName;
}
