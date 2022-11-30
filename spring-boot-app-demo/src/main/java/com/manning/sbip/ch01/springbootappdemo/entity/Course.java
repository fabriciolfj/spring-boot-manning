package com.manning.sbip.ch01.springbootappdemo.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;


@ToString
@Table(name = "COURSES")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedQueries({
        @NamedQuery(name = "Course.findAllByCategoryAndRating", query = "select c from Course c where c.category=?1 and c.rating=?2"),
        @NamedQuery(name = "Course.findAllByRating", query = "select c from Course c where c.rating=?1")
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CATEGORY")
    private String category;
    @Min(value = 1, message = "A course should have a minimum of 1 rating")
    @Max(value = 5, message = "A course should have a maximum of 5 rating")
    @Column(name = "RATING")
    private int rating;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToMany(mappedBy = "courses")
    private Set<Author> authors = new HashSet<>();

    public Course() { }

    public Course(String name, String category, int ration, String description) {
        this.name = name;
        this.category = category;
        this.rating = ration;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
