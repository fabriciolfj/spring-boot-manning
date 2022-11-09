package com.manning.sbip.ch01.springbootappdemo.service;

import com.manning.sbip.ch01.springbootappdemo.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationProperties applicationProperties;

    public String showProperties() {
        return applicationProperties.toString();
    }
}
