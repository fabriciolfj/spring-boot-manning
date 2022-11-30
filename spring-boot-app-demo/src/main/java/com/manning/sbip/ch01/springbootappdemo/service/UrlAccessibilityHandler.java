package com.manning.sbip.ch01.springbootappdemo.service;

import com.manning.sbip.ch01.springbootappdemo.exceptions.UrlNotAccessibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UrlAccessibilityHandler {

    @Value("${api.url:https://dog.ceo/}")
    private String url;

    @EventListener(classes = ContextRefreshedEvent.class) //é executado apos o application listener
    public void listener() {
        //throw new UrlNotAccessibleException(url);
    }
}
