package com.manning.sbip.ch01.springbootappdemo.exceptions.failureanalyzer;

import com.manning.sbip.ch01.springbootappdemo.exceptions.UrlNotAccessibleException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class UrlNotAccessibleFailureAnalyzer extends AbstractFailureAnalyzer<UrlNotAccessibleException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, UrlNotAccessibleException cause) {
        return new FailureAnalysis("Não foi possível acessar a url: " + cause.getUrl(),  "Valide a url e certifique-se de que esta acessivel", cause);
    }
}
