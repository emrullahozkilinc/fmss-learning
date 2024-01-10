package com.emr.springredistransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionalMethodService {

    private final RedisTemplate<String, Object> redisTemplate;

//    @Transactional
    public void saveNumbersUsingTransactionWithAnnotation(List<Integer> numbers)
    {
        redisTemplate.execute(new SessionCallback<>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                executeOperations(operations, numbers);
                return null;
            }
        });
    }

    private void executeOperations(RedisOperations operations, List<Integer> numbers) {
        operations.multi();

        StringBuilder redisValue = new StringBuilder("Saved numbers");
        for (Integer number : numbers) {
            log.info("Processed number is " + number);

            redisValue.append("___").append(getNumberAsText(number));
            operations.opsForValue().set("numbersAsText", redisValue.toString());
        }

        operations.exec();
    }

    private String getNumberAsText(Integer number)
    {
        return switch (number) {
            case 0 -> "zero";
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> throw new IllegalArgumentException("Bad number");
            default -> null;
        };
    }
}
