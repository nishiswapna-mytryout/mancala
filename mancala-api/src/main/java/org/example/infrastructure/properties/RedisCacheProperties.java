package org.example.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
@Getter
@Setter
public class RedisCacheProperties {

    private String host;
    private int port;

}
