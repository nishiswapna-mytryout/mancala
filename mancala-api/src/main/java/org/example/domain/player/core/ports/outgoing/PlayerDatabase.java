package org.example.domain.player.core.ports.outgoing;

import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.output.PlayerIdentifier;


public interface PlayerDatabase {

    PlayerIdentifier save(final Player player);

}
