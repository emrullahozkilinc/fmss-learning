package com.emr.connectionmodesredis;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;

@Configuration
class RedisStandaloneBeanConfiguration {

    @Bean
    public CacheManager cacheManager(){
        return RedisCacheManager.builder()
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .build();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * Lettuce
     */
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6303));
    }

    /**
     * Jedis
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration("localhost", 6303));
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .readFrom(REPLICA_PREFERRED)
                .build();

        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration("server", 6379);

        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

}
