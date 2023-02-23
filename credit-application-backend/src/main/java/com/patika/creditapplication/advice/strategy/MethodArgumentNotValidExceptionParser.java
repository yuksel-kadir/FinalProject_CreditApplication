package com.patika.creditapplication.advice.strategy;

import org.springframework.stereotype.Component;

@Component
public class MethodArgumentNotValidExceptionParser implements ErrorParseStrategy {
    private final String errorClass = "MethodArgumentNotValidException";

    @Override
    public String parseErrorMessage(String message) {
        String[] messages = message.split(";");
        String message2 = messages[messages.length - 1];
        return message2
                .split("\\[")[1]
                .replace("]", "");
    }

    @Override
    public String getErrorClass() {
        return this.errorClass;
    }
}
