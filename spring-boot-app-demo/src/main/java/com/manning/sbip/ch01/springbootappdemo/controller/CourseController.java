package com.manning.sbip.ch01.springbootappdemo.controller;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Api curso", description = "Esta api fornece serviços para gerenciar cursos")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Fornece todos os cursos disponíveis no aplicativo")
    public Iterable<Course> getAllCourses(@AuthenticationPrincipal Jwt jwt) {
        log.info("Usuario que solicitou: {}", jwt.getClaim("preferred_username").toString());
        return courseService.findAllCourses();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Fornece detalhes do id do curso fornecido")
    public Course getCourseById(@PathVariable("id") final long courseId) {
        return courseService.findCourseById(courseId);
    }

    @GetMapping("/category/{name}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Fornece detalhes dos cursos da categoria fornecida")
    public Iterable<Course> getCourseByCategory(@PathVariable("name") final String name) {
        return courseService.getCoursesByCategory(name);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Cria um novo curso")
    public Course createCourse(@RequestBody final Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualiza os detalhes do curso")
    public void updateCourse(@PathVariable("id") final long courseId, @RequestBody final Course course) {
        courseService.updateCourse(courseId, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui os detalhes do curso fornecido")
    public void deleteCourseById(@PathVariable("id") final long courseId) {
        courseService.deleteCourseById(courseId);
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui todos os cursos")
    public void deleteCourses() {
        courseService.deleteCourses();
    }

}
