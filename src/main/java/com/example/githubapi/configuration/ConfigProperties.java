package com.example.githubapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "prop")
@Getter
@Setter
public class ConfigProperties {
    private String githubBaseUrl;
    private String githubApiToken;
}
