package com.manning.sbip.ch01.springbootappdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    @Conditional(RelationDatabaseCondition.class)
    public RelationalDataSourceConfiguration dataSourceConfiguration() {
        return new RelationalDataSourceConfiguration();
    }
}
