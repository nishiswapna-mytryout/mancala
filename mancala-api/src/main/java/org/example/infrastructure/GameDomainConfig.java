package org.example.infrastructure;

import org.example.domain.GameBoardFeatures;
import org.example.domain.game.core.ports.GamePlayFacade;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;
import org.example.domain.game.infrastructure.GamePlayDatabaseAdaptar;
import org.example.domain.game.infrastructure.GamePlayRepository;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;
import org.example.infrastructure.properties.GameProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameDomainConfig {


    @Bean
    public GamePlay gamePlay(final GamePlayDatabase gamePlayDatabase, final GameBoardFeatures gameBoardFeatures , final PlayerDatabase playerDatabase) {
        return new GamePlayFacade(gamePlayDatabase, gameBoardFeatures, playerDatabase);
    }

    @Bean
    public GamePlayDatabase gamePlayDatabase(final GamePlayRepository gamePlayRepository) {
        return new GamePlayDatabaseAdaptar(gamePlayRepository);
    }

    @Bean
    public GameBoardFeatures gameBoardFeatures(final GameProperties gameProperties) {
        return GameBoardFeatures.initializeGame(gameProperties.getStoneCount());
    }

}
