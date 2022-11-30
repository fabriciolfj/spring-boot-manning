package com.manning.sbip.ch01.springbootappdemo.config;

import com.manning.sbip.ch01.springbootappdemo.service.CourseService;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigMetrics {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("app", "courses");
    }

    @Bean
    public Gauge createCoursesGauge(final MeterRegistry meterRegistry, final CourseService service) {
        return Gauge.builder("api.courses.created.gauge", service::count)
                .description("Total de cursos disponiveis")
                .register(meterRegistry);
    }

    @Bean
    public Timer createCoursesTimer(final MeterRegistry meterRegistry) {
        return Timer.builder("api.courses.creation.time")
                .description("Tempo de criacao do curso")
                .register(meterRegistry);
    }

    @Bean
    public DistributionSummary createCoursesSummary(final MeterRegistry meterRegistry) {
        return DistributionSummary.builder("api.courses.creation.summary")
                .register(meterRegistry);
    }
}
