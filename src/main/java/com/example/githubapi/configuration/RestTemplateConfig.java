package com.example.githubapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {
    @Value("${githubApiToken}")
    private String token;
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution)->{
            request.getHeaders().add("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        }));
        return restTemplate;
    }
}
