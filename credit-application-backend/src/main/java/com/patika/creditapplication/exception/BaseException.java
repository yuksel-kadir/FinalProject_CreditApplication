package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final String message;

    private final int httpStatus;

    public BaseException(String message) {
        this.message = message;
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
