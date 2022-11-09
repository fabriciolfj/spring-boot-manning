package com.manning.sbip.ch01.springbootappdemo;

import com.manning.sbip.ch01.springbootappdemo.config.ApplicationProperties;
import com.manning.sbip.ch01.springbootappdemo.config.DbConfiguration;
import com.manning.sbip.ch01.springbootappdemo.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Properties;

@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication
@ComponentScan
public class SpringBootAppDemoApplication {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootAppDemoApplication.class);

	public static void main(String[] args) {
		var app = new SpringApplication(SpringBootAppDemoApplication.class);

		final Properties properties = new Properties();
		properties.setProperty("spring.config.on-not-found", "ignore");

		app.setDefaultProperties(properties);

		final ConfigurableApplicationContext context = app.run(args);
		final DbConfiguration dbConfiguration = context.getBean(DbConfiguration.class);

		final Environment env = context.getBean(Environment.class);

		LOG.info("Value time limit app configuration {}", env.getProperty("app.timeout"));
		LOG.info(dbConfiguration.toString());

		final ApplicationService applicationService = context.getBean(ApplicationService.class);
		LOG.info(applicationService.showProperties());
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReadyEvent(ApplicationReadyEvent event) {
		System.out.println("Aplicativo esta pronto em " + new Date(event.getTimestamp()));
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			LOG.info("SpringBootAppDemoApplication CommandLineRunner executado");
		};
	}
}
