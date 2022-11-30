package com.manning.sbip.ch01.springbootappdemo.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseTrackerMetricsConfiguration {

    @Bean
    public Counter createCourseCounter(final MeterRegistry meterRegistry) {
        return Counter.builder("api.courses.created.count")
                .description("Numero de cursos criados")
                .register(meterRegistry);
    }
}
