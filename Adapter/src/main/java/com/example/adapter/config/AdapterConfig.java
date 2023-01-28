package com.example.adapter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("link")
@Data
public class AdapterConfig {
    private String baseUrl;
    private String fineRequest;
    private String fineResult;
    private String fineAcknowledge;
    private int timeout;
    private int retryCount;
}
