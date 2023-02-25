package com.patika.creditapplication.advice.strategy;

public class MismatchedInputExceptionParser implements ErrorParseStrategy{
    @Override
    public String parseErrorMessage(String message) {
        return null;
    }

    @Override
    public String getErrorClass() {
        return "MismatchedInputException";
    }
}
