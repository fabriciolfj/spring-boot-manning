package com.manning.sbip.ch01.springbootappdemo.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Course {

    private Long id;
    private String name;
    private String category;
    @Min(value = 1, message = "A course should have a minimum of 1 rating")
    @Max(value = 5, message = "A course should have a maximum of 5 rating")
    private int ration;

    public Course() { }

    public Course(Long id, String name, String category, int ration) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ration = ration;
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
