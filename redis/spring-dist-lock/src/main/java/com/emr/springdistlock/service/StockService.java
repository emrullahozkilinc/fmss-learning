package com.emr.springdistlock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class StockService {

    private final RedissonClient redissonClient;

    @Scheduled(cron = "0/1 * * * * ?")
    public void decreaseStock() {
        RAtomicLong stock = redissonClient.getAtomicLong("iPhone");
        RLock lock = redissonClient.getLock("iPhone-stock-lock");

        if (lock.tryLock()) {
            log.info("locked");
            try {
                lock.lock();
                stock.decrementAndGet();
            } finally {
                lock.unlock();
            }
        } else {
            log.warn("couldn't get lock. already locked!");
        }
    }
}
