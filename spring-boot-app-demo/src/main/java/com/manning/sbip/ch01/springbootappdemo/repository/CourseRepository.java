package com.manning.sbip.ch01.springbootappdemo.repository;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course, Long> {
}
