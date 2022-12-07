package com.manning.sbip.ch01.springbootappdemo.service;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.events.CourseEvents;
import com.manning.sbip.ch01.springbootappdemo.repository.CourseRepository;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final DistributionSummary createCoursesSummary;
    private final Timer createCoursesTimer;
    private final ApplicationEventPublisher applicationEventPublisher;

    @SneakyThrows
    public Course createCourse(final Course course) {
        createCoursesSummary.record(course.getRating());
        var result = createCoursesTimer.recordCallable(() -> repository.save(course));
        applicationEventPublisher.publishEvent(new CourseEvents(result));

        return result;
    }

    public Long count() {
        return repository.count();
    }


    public Optional<Course> findCourseById(Long courseId) {
        return repository.findById(courseId);
    }

    public Iterable<Course> findAllCourses() {
        return repository.findAll();
    }

    public Course updateCourse(Course course) {
        return repository.save(course);
    }

    public void deleteCourseById(Long courseId) {
        repository.deleteById(courseId);
    }
}
