package com.manning.sbip.ch01.springbootappdemo.service;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.events.CourseEvents;
import com.manning.sbip.ch01.springbootappdemo.exceptions.CourseNotFoundException;
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
    private static final String MESSAGE_NOT_FOUND = "Course %s not found";

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


    public Course findCourseById(Long courseId) {
        return repository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(String.format(MESSAGE_NOT_FOUND, courseId)));
    }

    public Iterable<Course> findAllCourses() {
        return repository.findAll();
    }

    public Course updateCourse(Course course) {
        return repository.save(course);
    }

    public void updateCourse(final Long id, final Course course) {
        repository.findById(id)
                .map(c -> {
                    c.setName(course.getName());
                    c.setCategory(course.getCategory());
                    c.setDescription(course.getDescription());
                    c.setRating(course.getRating());

                    return repository.save(c);
                })
                .orElseThrow(() -> new CourseNotFoundException(String.format(MESSAGE_NOT_FOUND, id)));
    }

    public void deleteCourseById(Long courseId) {
        var course = findCourseById(courseId);
        repository.delete(course);
    }

    public Iterable<Course> getCoursesByCategory(final String category) {
        return repository.findAllByCategory(category);
    }

    public void deleteCourses() {
        repository.deleteAll();
    }
}
