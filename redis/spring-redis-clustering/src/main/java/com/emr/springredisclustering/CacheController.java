package com.emr.springredisclustering;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CachingService cacheService;

    @PostMapping(path = "")
    public User cacheUser(@RequestBody User user) {
        return cacheService.cacheUser(user);
    }

    @GetMapping(path = "/{name}")
    public User getCachedUserByName(@PathVariable String name) {
        return cacheService.getCachedUserByName(name);
    }
}
