package org.example.domain.player.infrastructure;

import org.example.domain.player.core.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
}
