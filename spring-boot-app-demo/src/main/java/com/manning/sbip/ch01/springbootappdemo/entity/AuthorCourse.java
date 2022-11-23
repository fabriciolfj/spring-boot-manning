package com.manning.sbip.ch01.springbootappdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors_courses")
public class AuthorCourse {

    @Id
    @Column(name = "author_id")
    private long authorId;
    @Column(name = "course_id")
    private long courseId;
}
