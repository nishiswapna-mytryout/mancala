package org.example.config;

import org.example.config.properties.RedisCacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class CacheConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(final RedisCacheProperties redisCacheProperties) {
        RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
        redisConf.setHostName(redisCacheProperties.getHost());
        redisConf.setPort(redisCacheProperties.getPort());
        return new LettuceConnectionFactory(redisConf);
    }

    @Bean
    public RedisCacheManager cacheManager(final RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .transactionAware()
                .build();
    }
}
