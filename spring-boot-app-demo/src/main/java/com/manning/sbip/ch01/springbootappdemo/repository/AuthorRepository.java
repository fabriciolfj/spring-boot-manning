package com.manning.sbip.ch01.springbootappdemo.repository;

import com.manning.sbip.ch01.springbootappdemo.dto.AuthorCourseDto;
import com.manning.sbip.ch01.springbootappdemo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT new com.manning.sbip.ch01.springbootappdemo.dto.AuthorCourseDto(c.id, a.name, c.name, c.description) " +
            "FROM Author a, Course c, AuthorCourse ac " +
            "where a.id = ac.authorId " +
            "and c.id = ac.courseId " +
            "and ac.authorId=?1")
    Iterable<AuthorCourseDto> getAuthorCourseInfo(long authorId);
}
