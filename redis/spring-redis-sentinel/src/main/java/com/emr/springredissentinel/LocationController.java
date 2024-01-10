package com.emr.springredissentinel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}
