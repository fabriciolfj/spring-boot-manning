package com.github.fabriciolfj.courseservice.controller;

import com.github.fabriciolfj.courseservice.model.Course;
import com.github.fabriciolfj.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public Flux<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Course>> getCourseById(@PathVariable final String id) {
        return courseRepository.findById(id)
                .map(c -> ResponseEntity.ok(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{name}")
    public Flux<Course> getCourseByCategory(@PathVariable("name") final String name) {
        return courseRepository.findAllByCategory(name)
                .doOnError(e -> log.info("Falha ao buscar os cursos"));
    }

    @PostMapping
    public Mono<Course> createCourse(@RequestBody final Course course) {
        return courseRepository.save(course)
                .doOnSuccess(c -> log.info("Curso criado com sucesso"))
                .doOnError(e -> log.error("Falha ao criar o curso. Detalhes: {}", e.getMessage()));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Course>> updateCourse(@PathVariable("id") final String id,
                                                     @RequestBody final Course course) {
        return this.courseRepository.findById(id)
                .flatMap(c -> {
                    c.setName(course.getName());
                    c.setDescription(course.getDescription());
                    c.setRating(course.getRating());
                    c.setCategory(course.getCategory());
                    return courseRepository.save(c);
                }).map(c -> ResponseEntity.ok(c))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(e -> log.error("Falha ao atualizar o curso {}, detalhes {}", id, e.getMessage()));
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Course>> deleteCourseById(@PathVariable("id") final String id) {
        return this.courseRepository.findById(id)
                .flatMap(c -> this.courseRepository.delete(c).then(Mono.just(ResponseEntity.ok(c))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteCourses() {
        return courseRepository.deleteAll();
    }
}
