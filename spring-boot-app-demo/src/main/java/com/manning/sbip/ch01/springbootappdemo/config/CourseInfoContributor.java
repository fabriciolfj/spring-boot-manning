package com.manning.sbip.ch01.springbootappdemo.config;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.repository.CourseRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CourseInfoContributor implements InfoContributor {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void contribute(Info.Builder builder) {
        var courses = courseRepository.findAll();
        var coursesRatingName = new ArrayList<CourseNameRating>();

        for (Course course: courses) {
            coursesRatingName.add(CourseNameRating.builder()
                            .name(course.getName())
                            .classification(course.getRating())
                    .build());
        }

        builder.withDetail("courses", coursesRatingName).build();
    }

    @Builder
    @Data
    private static class CourseNameRating {
        String name;
        int classification;
    }
}
