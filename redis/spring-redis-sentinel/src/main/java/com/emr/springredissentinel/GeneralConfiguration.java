package com.emr.springredissentinel;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
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
