package com.github.fabriciolfj.courseservice.repository;

import com.github.fabriciolfj.courseservice.model.Course;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CourseRepository extends ReactiveMongoRepository<Course, String> {

    Flux<Course> findAllByCategory(final String category);
}
