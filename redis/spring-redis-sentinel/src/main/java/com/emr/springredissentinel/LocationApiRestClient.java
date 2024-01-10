package com.emr.springredissentinel;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
