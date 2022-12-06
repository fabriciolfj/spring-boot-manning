package com.manning.sbip.ch01.springbootappdemo.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class DogsApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            ParameterizedTypeReference<Map<String, String>> ref = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<Map<String, String>> result =
                    new RestTemplate().exchange("https://dog.ceo/api/breeds/image/randomx", HttpMethod.GET, null, ref);

            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                return Health.up().withDetails(result.getBody()).build();
            }

            return Health.down().withDetail("status", result.getStatusCode()).build();
        } catch (RestClientException e) {
            return Health.down().withException(e).build();
        }
    }
}
