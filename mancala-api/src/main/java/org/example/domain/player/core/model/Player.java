package org.example.domain.player.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player")
@RequiredArgsConstructor
@Getter
public class Player {

    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @Id
    private String playerId;

}

