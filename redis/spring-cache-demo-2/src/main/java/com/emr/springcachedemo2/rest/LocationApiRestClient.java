package com.emr.springcachedemo2.rest;

import com.emr.springcachedemo2.dto.LocationApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationApiRestClient {

    @Value("${api.location.url}")
    private String locationUrl;

    private final RestTemplate restTemplate;

    public LocationApiResponse getLocationInfo() {
        return restTemplate.getForObject(locationUrl, LocationApiResponse.class);
    }
}