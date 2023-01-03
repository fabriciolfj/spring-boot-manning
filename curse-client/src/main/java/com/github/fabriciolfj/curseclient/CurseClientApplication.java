package com.github.fabriciolfj.curseclient;

import com.github.fabriciolfj.curseclient.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurseClientApplication implements CommandLineRunner {

	@Autowired
	private WebClientApi clientApi;

	public static void main(String[] args) {
		SpringApplication.run(CurseClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		clientApi.postNewCourse(Course.builder()
						.description("teste")
						.name("teste")
						.rating(0)
						.category("teste")
						.build())
				.thenMany(clientApi.getAllCourses())
				.subscribe(System.out::println);
	}
}
