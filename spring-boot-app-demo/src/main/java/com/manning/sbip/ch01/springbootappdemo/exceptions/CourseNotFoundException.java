package com.manning.sbip.ch01.springbootappdemo.exceptions;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(final String message) {
        super(message);
    }
}
