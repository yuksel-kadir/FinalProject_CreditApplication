package com.patika.creditapplication.advice.strategy;

import org.springframework.stereotype.Component;

@Component
public class HttpMessageNotReadableExceptionParser implements ErrorParseStrategy {
    private final String errorClass = "HttpMessageNotReadableException";

    @Override
    public String parseErrorMessage(String message) {
        return message.split(":")[0].trim() + ".";
    }

    @Override
    public String getErrorClass() {
        return this.errorClass;
    }
}
