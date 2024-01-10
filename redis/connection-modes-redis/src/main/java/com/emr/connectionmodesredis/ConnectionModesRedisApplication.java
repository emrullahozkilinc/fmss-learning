package com.emr.connectionmodesredis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@RequiredArgsConstructor
@SpringBootApplication
public class ConnectionModesRedisApplication implements CommandLineRunner {

    private final StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ConnectionModesRedisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stringRedisTemplate.opsForList().leftPush("userId", "URL");
    }
}
