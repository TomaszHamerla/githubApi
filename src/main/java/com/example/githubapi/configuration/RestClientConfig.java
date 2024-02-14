package com.example.githubapi.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
    private final ConfigProperties prop;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(prop.getGithubBaseUrl())
                .defaultHeader("Authorization", "Bearer " + prop.getGithubApiToken())
                .build();
    }
}
