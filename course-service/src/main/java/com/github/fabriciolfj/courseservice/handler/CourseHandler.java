package com.github.fabriciolfj.courseservice.handler;

import com.github.fabriciolfj.courseservice.model.Course;
import com.github.fabriciolfj.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CourseHandler {

    private final CourseRepository courseRepository;

    public Mono<ServerResponse> findAllCourses(final ServerRequest request) {
        var cursos = this.courseRepository.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(cursos, Course.class);
    }

    public Mono<ServerResponse> findCourseById(final ServerRequest request) {
        final var courseId = request.pathVariable("id");
        var courseMono = this.courseRepository.findById(courseId);

        return courseMono.flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createCourse(final ServerRequest request) {
        var course = request.bodyToMono(Course.class);

        return course.flatMap(c ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(courseRepository.save(c), Course.class));
    }

    public Mono<ServerResponse> updateCourse(final ServerRequest request) {
        var id = request.pathVariable("id");
        var courseMono = this.courseRepository.findById(id);
        var newCourseMono = request.bodyToMono(Course.class);

        return newCourseMono.zipWith(courseMono, (newCourse, exists) ->
                        Course.builder()
                                .id(exists.getId())
                                .name(newCourse.getName())
                                .category(newCourse.getCategory())
                                .description(newCourse.getDescription())
                                .rating(newCourse.getRating()).build())
                .flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(courseRepository.save(c), Course.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> deleteCourse(final ServerRequest request) {
        var id = request.pathVariable("id");
        return courseRepository.findById(id)
                .flatMap(c -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(courseRepository.delete(c))))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteAll(final ServerRequest request){
        return ServerResponse.ok().build(this.courseRepository.deleteAll());
    }
}
