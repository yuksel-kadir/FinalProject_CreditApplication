package com.patika.creditapplication.advice.strategy;

import org.springframework.stereotype.Component;

@Component
public class DateTimeParseExceptionParser implements ErrorParseStrategy{
    private final String errorClass = "DateTimeParseException";

    @Override
    public String parseErrorMessage(String message) {
        return "The date format is wrong. The date format should be 'yyyy-mm-dd'. Please check your date parameter.";
    }

    @Override
    public String getErrorClass() {
        return this.errorClass;
    }
}
