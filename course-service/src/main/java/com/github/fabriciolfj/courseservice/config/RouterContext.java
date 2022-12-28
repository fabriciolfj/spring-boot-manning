package com.github.fabriciolfj.courseservice.config;

import com.github.fabriciolfj.courseservice.handler.CourseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterContext {

    @Bean
    RouterFunction<ServerResponse> routes(final CourseHandler handler) {
        return RouterFunctions.route(GET("/v2/courses").and(accept(MediaType.APPLICATION_JSON)), handler::findAllCourses)
                .andRoute(GET("/v2/courses/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findCourseById)
                .andRoute(POST("/v2/courses").and(accept(MediaType.APPLICATION_JSON)), handler::createCourse)
                .andRoute(PUT("/v2/courses/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::updateCourse)
                .andRoute(DELETE("/2/courses/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteCourse)
                .andRoute(DELETE("/v2/courses").and(accept(MediaType.APPLICATION_JSON)), handler::deleteAll);
    }
}
