package org.example.infrastructure;

import org.example.domain.player.core.ports.PlayerFacade;
import org.example.domain.player.core.ports.incoming.AddNewPlayer;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;
import org.example.domain.player.infrastructure.PlayerDatabaseAdaptar;
import org.example.domain.player.infrastructure.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerDomainConfig {

    @Bean
    public AddNewPlayer addNewPlayer(final PlayerDatabase playerDatabase){
        return new PlayerFacade(playerDatabase);
    }

    @Bean
    public PlayerDatabase playerDatabase(final PlayerRepository playerRepository){
        return new PlayerDatabaseAdaptar(playerRepository);
    }

}
