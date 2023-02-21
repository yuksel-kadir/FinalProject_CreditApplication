package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ClientNotFoundException extends RuntimeException {

    private final String message;

    private final int httpStatus;

    public ClientNotFoundException() {
        this.message = "Client not found.";
        httpStatus = HttpStatus.NOT_FOUND.value();
    }
}
