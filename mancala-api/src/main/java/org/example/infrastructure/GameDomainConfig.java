package org.example.infrastructure;

import org.example.domain.game.core.ports.GamePlayFacade;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;
import org.example.domain.game.infrastructure.GamePlayDatabaseAdaptar;
import org.example.domain.game.infrastructure.GamePlayRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameDomainConfig {

    @Bean
    public GamePlay gamePlay(final GamePlayDatabase gamePlayDatabase) {
        return new GamePlayFacade(gamePlayDatabase);
    }

    @Bean
    public GamePlayDatabase gamePlayDatabase(final GamePlayRepository gamePlayRepository) {
        return new GamePlayDatabaseAdaptar(gamePlayRepository);
    }

}
