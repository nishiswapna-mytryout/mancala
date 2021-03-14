package org.example.domain.game.core.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class NewGameCommand {

    @NotNull(message = "player id cannot be null")
    @NotBlank(message = "Invalid player Id A")
    private String playerIdA;

    @NotNull(message = "player id cannot be null")
    @NotBlank(message = "Invalid player Id B")
    private String playerIdB;
}
