package com.manning.sbip.ch01.springbootappdemo.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("application inicializando em " + new Date(event.getTimestamp()));
    }
}
