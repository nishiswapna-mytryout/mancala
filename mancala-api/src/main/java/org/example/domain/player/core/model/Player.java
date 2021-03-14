package org.example.domain.player.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.function.Supplier;

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

    public Player validatePlayer(Supplier<? extends RuntimeException> exceptionSupplier) {
        if (this.firstName.trim().length() == 0 || this.lastName.trim().length() == 0) {
            throw exceptionSupplier.get();
        }
        return this;
    }
}

