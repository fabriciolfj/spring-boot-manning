package com.github.fabriciolfj.curseclient;

import com.github.fabriciolfj.curseclient.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientApi {

    private static final String BASE_URL = "http://localhost:8080/v1/courses";

    private WebClient webClient;

    public WebClientApi() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public Mono<ResponseEntity<Course>> postNewCourse(final Course course) {
        return this.webClient
                .post()
                .body(Mono.just(course), Course.class)
                .retrieve()
                .toEntity(Course.class)
                .doOnSuccess(System.out::println);
    }

    public Mono<Course> updateCourse(final String id, final String name, final String category, final int rating, final String description) {
        return this.webClient
                .put()
                .uri("{id}", id)
                .body(Mono.just(Course.builder()
                                .category(category)
                                .name(name)
                                .id(id)
                                .rating(rating)
                                .description(description)
                        .build()), Course.class)
                .retrieve()
                .bodyToMono(Course.class)
                .doOnSuccess(System.out::println);
    }

    public Mono<Course> getCourseById(final String id) {
        return this.webClient
                .get()
                .uri("{uri}", id)
                .retrieve()
                .bodyToMono(Course.class)
                .doOnSuccess(System.out::println)
                .doOnError(e -> System.out.println(e.getMessage()));
    }

    public Flux<Course> getAllCourses() {
        return this.webClient
                .get()
                .retrieve()
                .bodyToFlux(Course.class)
                .doOnNext(System.out::println)
                .doOnError(e -> System.out.println(e.getMessage()));
    }

    public Mono<Void> deleteCourse(final String id) {
        return this.webClient
                .delete()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(e -> System.out.println("Delete " + e))
                .doOnError(e -> System.out.println(e.getMessage()));
    }
}
