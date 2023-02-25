package com.patika.creditapplication.advice;

import com.patika.creditapplication.advice.strategy.ErrorParseStrategy;
import com.patika.creditapplication.exception.*;
import com.patika.creditapplication.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationControllerAdvice {
    private final List<ErrorParseStrategy> parseStrategies;

    private ErrorParseStrategy getStrategy(String className) {
        return parseStrategies.stream()
                .filter(errorParseStrategy -> errorParseStrategy.getErrorClass().equals(className))
                .findFirst()
                .orElseThrow(ErrorParseStrategyNotFoundException::new);
    }

    @ExceptionHandler(ErrorParseStrategyNotFoundException.class)
    public ResponseEntity<Response> handleErrorParseStrategyNotFound(ErrorParseStrategyNotFoundException ex) {
        return new ResponseEntity<>(
                new Response(ex.getHttpStatus(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(CreditApplicationNotFoundException.class)
    public ResponseEntity<Response> handleCreditApplicationNotFound(CreditApplicationNotFoundException ex) {
        return new ResponseEntity<>(
                new Response(ex.getHttpStatus(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String exceptionClassName = ex.getClass().getSimpleName().trim();
        ErrorParseStrategy strategy = getStrategy(exceptionClassName);
        String message = strategy.parseErrorMessage(ex.getMessage());
        return new ResponseEntity<>(
                new Response(400, message),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleNotReadable(HttpMessageNotReadableException ex) {
        String exceptionClassName = ex.getMostSpecificCause().getClass().getSimpleName().trim();
        ErrorParseStrategy strategy = getStrategy(exceptionClassName);
        String message = strategy.parseErrorMessage(ex.getMessage());
        return new ResponseEntity<>(
                new Response(400, "message"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ClientExistsException.class)
    public ResponseEntity<Response> handleClientExists(ClientExistsException ex) {
        return new ResponseEntity<>(
                new Response(ex.getHttpStatus(), ex.getMessage()),
                HttpStatus.FOUND
        );
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Response> handleClientNotFound(ClientNotFoundException ex) {
        return new ResponseEntity<>(
                new Response(ex.getHttpStatus(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CreditStrategyNotFoundException.class)
    public ResponseEntity<Response> handleCreditStrategyNotFound(CreditStrategyNotFoundException ex) {
        return new ResponseEntity<>(
                new Response(ex.getHttpStatus(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


}
