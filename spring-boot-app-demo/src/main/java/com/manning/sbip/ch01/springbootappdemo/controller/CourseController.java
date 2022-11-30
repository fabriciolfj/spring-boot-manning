package com.manning.sbip.ch01.springbootappdemo.controller;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService service;

    @PostMapping
    public Course process(@RequestBody final Course course) {
        return service.createCourse(course);
    }
}
