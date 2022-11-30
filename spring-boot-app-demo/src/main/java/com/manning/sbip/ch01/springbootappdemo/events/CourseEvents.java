package com.manning.sbip.ch01.springbootappdemo.events;

import org.springframework.context.ApplicationEvent;

public class CourseEvents extends ApplicationEvent {

    public CourseEvents(Object source) {
        super(source);
    }
}
