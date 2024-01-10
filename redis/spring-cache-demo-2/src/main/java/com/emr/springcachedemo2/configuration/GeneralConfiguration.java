package com.emr.springcachedemo2.configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class GeneralConfiguration {

    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapper.builder().build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
