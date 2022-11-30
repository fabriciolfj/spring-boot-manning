package com.manning.sbip.ch01.springbootappdemo.exceptions;

import lombok.Getter;

@Getter
public class UrlNotAccessibleException extends RuntimeException {

    private String url;

    public UrlNotAccessibleException(final String url) {
        this(url, null);
    }

    public UrlNotAccessibleException(final String url, final Throwable cause) {
        super("URL " + url + " nao esta acessivel", cause);
        this.url = url;
    }
}
