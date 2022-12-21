package com.manning.sbip.ch01.springbootappdemo.controller;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public Iterable<Course> getAllCourses() {
        return courseService.findAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable("id") final long courseId) {
        return courseService.findCourseById(courseId);
    }

    @GetMapping("/category/{name}")
    public Iterable<Course> getCourseByCategory(@PathVariable("name") final String name) {
        return courseService.getCoursesByCategory(name);
    }

    @PostMapping
    public Course createCourse(@RequestBody final Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") final long courseId, @RequestBody final Course course) {
        courseService.updateCourse(courseId, course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable("id") final long courseId) {
        courseService.deleteCourseById(courseId);
    }

    @DeleteMapping
    public void deleteCourses() {
        courseService.deleteCourses();
    }

}
