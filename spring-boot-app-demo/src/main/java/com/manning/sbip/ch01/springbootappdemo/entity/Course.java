package com.manning.sbip.ch01.springbootappdemo.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Table(name = "COURSES")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    private int ration;
    @Column(name = "DESCRIPTION")
    private String description;

    public Course() { }

    public Course(String name, String category, int ration, String description) {
        this.name = name;
        this.category = category;
        this.ration = ration;
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

    public int getRation() {
        return ration;
    }

    public void setRation(int ration) {
        this.ration = ration;
    }
}
