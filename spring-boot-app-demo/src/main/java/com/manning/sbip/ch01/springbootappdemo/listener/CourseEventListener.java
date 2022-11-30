package com.manning.sbip.ch01.springbootappdemo.listener;

import com.manning.sbip.ch01.springbootappdemo.events.CourseEvents;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseEventListener implements ApplicationListener<CourseEvents> {

    private final Counter createCourseCounter;

    @Override
    public void onApplicationEvent(CourseEvents event) {
        log.info("Event received : {}", event.getSource());
        createCourseCounter.increment();
    }
}
