package com.manning.sbip.ch01.springbootappdemo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUTHORS")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String bio;
    @ManyToMany
    @JoinTable(name = "AUTHOR_COURSES", joinColumns = {
            @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)})
    private Set<Course> courses = new HashSet<>();

    public Author() { }

    public Author(final String name, final String bio) {
        this.name = name;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", courses=" + courses +
                '}';
    }
}
