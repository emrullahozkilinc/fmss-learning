package com.emr.springcachedemo2.service;

import com.emr.springcachedemo2.dto.LocationApiResponse;
import com.emr.springcachedemo2.rest.LocationApiRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationApiRestClient client;

    @Cacheable("locations")
    public LocationApiResponse getAll() {
        log.warn("location api method worked");
        return client.getLocationInfo();
    }

    @Cacheable(value = "locations2", key = "#districtId")
    public LocationApiResponse.CityData getLocationInfoById(int districtId) {
        log.warn("location api method worked with id: {}", districtId);
        LocationApiResponse response = getAll();
        return response.data().stream()
                .filter(cityData -> cityData.id() == districtId)
                .findFirst()
                .orElseThrow();
    }

    @CachePut(value = "locations3", key = "city.id()")
    public LocationApiResponse.CityData putLocation(LocationApiResponse.CityData city) {
        log.warn("location api putLocation method worked");
        return new LocationApiResponse.CityData(city.id(), "",0,0,0,null,false,null,null,null,null);
    }

    @Scheduled(cron = "*/10 * * * * *")
    @CacheEvict(value = "locations")
    public void clearLocationCache(){
        log.warn("location cache cleared");
    }


    @Cacheable(value = "asd",key = "#cacheCondition ? 'a' : 'b'")
    public Set<String> cacheWithCondition(boolean cacheCondition){
        log.info("cache with condition worked with paramater: {}", cacheCondition);
        return Collections.singleton("asdasd");
    }
}
