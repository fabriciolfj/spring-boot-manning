package com.manning.sbip.ch01.springbootappdemo;

import com.manning.sbip.ch01.springbootappdemo.config.ApplicationProperties;
import com.manning.sbip.ch01.springbootappdemo.config.DbConfiguration;
import com.manning.sbip.ch01.springbootappdemo.entity.User;
import com.manning.sbip.ch01.springbootappdemo.service.ApplicationService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;


import java.util.Date;
import java.util.Properties;

@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication
@ComponentScan
public class SpringBootAppDemoApplication implements HealthIndicator {

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
		LOG.info("Aplicativo esta pronto em {}",  new Date(event.getTimestamp()));
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			/*final Course course = new Course();
			course.setId(1L);
			course.setRation(0);

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			var violations = validator.validate(course);

			violations.forEach(c -> LOG.error("fail violation, details: {}", c.getMessage()));*/

			User user1 = User.builder().userName("user1").password("123SS").build();
			User user2 = User.builder().userName("user2").password("F@bricio12").build();

			Validator validation = Validation.buildDefaultValidatorFactory().getValidator();
			var violations = validation.validate(user1);

			violations.forEach(s -> LOG.error("User 1 não atendeu a validacao do password: {}", s.getMessage()));

			violations = validation.validate(user2);
			violations.forEach(s -> LOG.error("User 2 não atendeu a validacao do password: {}", s.getMessage()));
		};
	}

	public Health health() {
		return Health.status("FATAL").build();
	}
}
