package com.manning.sbip.ch01.springbootappdemo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@ConfigurationProperties("app.sbip.ct")
public class ApplicationProperties {

    private final String name;
    private final String ip;
    private final int port;
    private final Security security;

    @ToString
    @Getter
    @AllArgsConstructor
    public static class Security {

        private final boolean enabled;
        private final String token;
        private final List<String> roles;
    }

}
