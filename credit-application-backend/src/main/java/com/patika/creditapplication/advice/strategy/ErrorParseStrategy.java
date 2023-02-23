package com.patika.creditapplication.advice.strategy;

public interface ErrorParseStrategy {
    String parseErrorMessage(String message);
    String getErrorClass();
}
