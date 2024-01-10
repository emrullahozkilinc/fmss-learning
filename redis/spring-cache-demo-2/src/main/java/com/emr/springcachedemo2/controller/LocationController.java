package com.emr.springcachedemo2.controller;

import com.emr.springcachedemo2.dto.LocationApiResponse;
import com.emr.springcachedemo2.rest.LocationApiRestClient;
import com.emr.springcachedemo2.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @GetMapping("/all")
    public ResponseEntity<LocationApiResponse> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationApiResponse.CityData> getAll(@PathVariable int id) {
        return ResponseEntity.ok(service.getLocationInfoById(id));
    }

    @GetMapping("/cache-with-condition/{condition}")
    public ResponseEntity<Set<String>> cacheWithCondition(@PathVariable boolean condition) {
        return ResponseEntity.ok(service.cacheWithCondition(condition));
    }
}
