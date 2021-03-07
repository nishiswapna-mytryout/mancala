package org.example.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mancala")
@Getter
@Setter
public class GameProperties {

    @Range(min = 1, message = "GameCannot be initialized with less than 1 stone")
    private int stoneCount;
}
