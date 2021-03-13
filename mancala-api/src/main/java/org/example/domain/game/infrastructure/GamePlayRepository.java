package org.example.domain.game.infrastructure;

import org.example.domain.game.core.model.GameState;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GamePlayRepository extends MongoRepository<GameState, String> {
}
