package com.emr.springreactiveredis;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final ReactiveRedisOperations<String, User> reactiveRedisOperations;
    private String key = "users";

    public Flux<User> findAllUsers() {
        return this.reactiveRedisOperations.opsForList().range(this.key, 0, -1);
    }

    public Mono<User> findUserById(String id) {
        return this.findAllUsers().filter(p -> p.getId().equals(id))
                .switchIfEmpty(Mono.just(User.builder().build()))
                .last();
    }

    public Mono<String> saveUser(User user) {
        user.setId(UUID.randomUUID().toString());

        Duration ttl = Duration.ofSeconds(60); // Set TTL to 60 seconds

//        No TTL
//        reactiveRedisOperations.opsForList().rightPush(this.key, user);

        return reactiveRedisOperations.opsForList()
                .rightPush(key, user)
                .flatMap(result -> reactiveRedisOperations.expire(this.key, ttl))
                .map(result -> "List set with TTL");

    }

    public Mono<Boolean> deleteAllUsers() {
        return this.reactiveRedisOperations.opsForList().delete(this.key);
    }

    public Mono<Long> publishUser(User user) {
        user.setId(UUID.randomUUID().toString());
        return this.reactiveRedisOperations.convertAndSend(this.key, user);
    }

}
