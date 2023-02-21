package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ClientExistsException extends RuntimeException {

    private final String message;

    private final int httpStatus;

    public ClientExistsException() {
        this.message = "The client already exists.";
        httpStatus = HttpStatus.FOUND.value();
    }
}
