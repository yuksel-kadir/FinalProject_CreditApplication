package com.patika.creditapplication.advice.strategy;

import org.springframework.stereotype.Component;

@Component
public class InvalidFormatExceptionParser implements ErrorParseStrategy {
    private final String errorClass = "InvalidFormatException";

    @Override
    public String parseErrorMessage(String message) {
        return message.split("from")[1]
                .split(" at")[0]
                .replace("\n", "")
                .trim();
    }

    @Override
    public String getErrorClass() {
        return this.errorClass;
    }
}
