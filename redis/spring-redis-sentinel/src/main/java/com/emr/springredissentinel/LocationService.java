package com.emr.springredissentinel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationApiRestClient restClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Cacheable("locations")
    public LocationApiResponse getAll() {
        log.warn("location api method worked");
//        redisTemplate.opsForValue().set("locs",restClient.getLocationInfo() );
        return restClient.getLocationInfo();
    }

    @Cacheable(value = "locations")
    public LocationApiResponse.CityData getLocationInfoById(int districtId) {
        log.warn("location api method worked with id: {}", districtId);
        LocationApiResponse response = restClient.getLocationInfo();
        return response.data().stream()
                .filter(cityData -> cityData.id() == districtId)
                .findFirst()
                .orElseThrow();
    }
}
