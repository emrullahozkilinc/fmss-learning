package com.emr.springredisclustering;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CachingService {

    private final RedisTemplate<String, Object> redisTemplate;

    public User cacheUser(User user){
        redisTemplate.opsForValue().set(user.getName(), user, Duration.ofMinutes(2L));
        return user;
    }

    public User getCachedUserByName(String name){
        var user = redisTemplate.opsForValue().get(name);
        return (User) user;
    }
}
