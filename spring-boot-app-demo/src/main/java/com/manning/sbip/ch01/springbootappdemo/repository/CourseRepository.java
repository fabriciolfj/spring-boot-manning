package com.manning.sbip.ch01.springbootappdemo.repository;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, QuerydslPredicateExecutor<Course>, PagingAndSortingRepository<Course, Long> {

    //Iterable<Course> findAllByCategory(final String category);
    Iterable<Course> findAllByCategoryOrderByName(final String category);
    boolean existsByName(final String name);
    long countByCategory(final String category);
    Iterable<Course> findByNameOrCategory(final String name, final String category);
    Iterable<Course> findByNameStartsWith(final String name);
    Stream<Course> streamAllByCategory(final String category);
    List<Course> findByCategoryOrderByNameDesc(final String category);
    Iterable<Course> findAllByCategoryAndRating(final String category, int rating);
    @Query("select c from Course c where c.category = ?1")
    Iterable<Course> findAllByCategory(final String category);
    @Query("select c from Course c where c.category =:category and c.rating > :rating")
    Iterable<Course> findAllByCategoryAnRatingGreaterThan(@Param("category") final String category, @Param("rating") final int rating);
    @Query(value = "select * from COURSES where RATING =?1", nativeQuery = true)
    Iterable<Course> findAllByRating(int rating);
    @Modifying
    @Transactional
    @Query("update Course c set c.rating = :rating where c.name = :name")
    int updateCourseRatingByName(@Param("rating") int rating, @Param("name") final String name);
}
