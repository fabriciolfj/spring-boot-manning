package com.github.fabriciolfj.courseservice.config;

import com.github.fabriciolfj.courseservice.model.Course;
import com.github.fabriciolfj.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CourseConfig implements CommandLineRunner {

    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        var course1 = Course
                .builder()
                .id("1")
                .name("java")
                .category("ti")
                .description("java programing")
                .rating(5)
                .build();

        var course2 = Course
                .builder()
                .id("2")
                .name("python")
                .category("ti")
                .description("python programing")
                .rating(5)
                .build();

        var course3 = Course
                .builder()
                .id("3")
                .name("mecanica")
                .category("geral")
                .description("mecaninca geral")
                .rating(5)
                .build();

        var list = List.of(course1, course2, course3);

        courseRepository.deleteAll().subscribe();

        Flux.fromIterable(list)
                .flatMap(courseRepository::save)
                .thenMany(courseRepository.findAll())
                .subscribe(System.out::println);
    }
}
