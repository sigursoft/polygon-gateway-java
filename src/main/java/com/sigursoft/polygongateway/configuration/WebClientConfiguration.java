package com.sigursoft.polygongateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private final String polygonBaseUrl, polygonAccessToken;

    public WebClientConfiguration(
            @Value("${polygon.baseUrl}") String polygonBaseUrl,
            @Value("${polygon.token}")String polygonAccessToken
    ) {
        this.polygonBaseUrl = polygonBaseUrl;
        this.polygonAccessToken = polygonAccessToken;
    }

    @Bean
    public WebClient polygon() {
        return WebClient.builder().baseUrl(polygonBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + polygonAccessToken).build();
    }
}
