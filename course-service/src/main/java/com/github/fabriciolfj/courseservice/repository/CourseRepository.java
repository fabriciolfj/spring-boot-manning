package com.github.fabriciolfj.courseservice.repository;

import com.github.fabriciolfj.courseservice.model.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CourseRepository extends ReactiveCrudRepository<Course, String> {

    Flux<Course> findAllByCategory(final String category);
}
