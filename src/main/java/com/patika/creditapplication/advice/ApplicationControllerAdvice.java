package com.patika.creditapplication.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Objects;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleBadRequest(MethodArgumentNotValidException ex) {
        //Parse error message
        String[] messages = ex.getMessage().split(";");
        String message = messages[messages.length - 1];
        message = message
                .split("\\[")[1]
                .replace("]", "");

        return new ResponseEntity<>(
                new Response(400, message),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleNotReadable(HttpMessageNotReadableException ex) {
        Object exClass = ex.getMostSpecificCause().getClass();
        if (exClass.equals(DateTimeParseException.class)) {
            String message = "The date format is wrong. The date format should be 'yyyy-mm-dd'. Please check your date parameter.";
            return new ResponseEntity<>(new Response(400, message), HttpStatus.BAD_REQUEST);
        }
        if (exClass.equals(InvalidFormatException.class)) {
            String message = Objects.requireNonNull(ex.getRootCause()).getMessage()
                    .split("from")[1]
                    .split(" at")[0]
                    .replace("\n", "")
                    .trim();
            return new ResponseEntity<>(new Response(400, message), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Response(400, Objects.requireNonNull(ex.getRootCause()).getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientExistsException.class)
    public ResponseEntity<Response> handleClientExists(ClientExistsException ex) {
        return new ResponseEntity<>(
                new Response(ex.getHttpStatus(), ex.getMessage()),
                HttpStatus.FOUND
        );
    }

}
