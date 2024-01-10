package com.emr.springredissentinel;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig implements CachingConfigurer {

    private final RedisProperties redisProperties;

    @Bean
    protected LettuceConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(redisProperties.getSentinel().getMaster());
        redisProperties.getSentinel().getNodes().forEach(s -> sentinelConfig.sentinel(s, redisProperties.getPort()));

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(30000))
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();

        return new LettuceConnectionFactory(sentinelConfig, clientConfig);
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager() {
        return RedisCacheManager.builder(this.redisConnectionFactory()).cacheDefaults(this.cacheConfiguration())
                .build();
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())); //genel konfigürasyon
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.info("Failure getting from cache: " + cache.getName() + ", exception: " + exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.info("Failure putting into cache: " + cache.getName() + ", exception: " + exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.info("Failure evicting from cache: " + cache.getName() + ", exception: " + exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.info("Failure clearing cache: " + cache.getName() + ", exception: " + exception);
            }
        };
    }
}
