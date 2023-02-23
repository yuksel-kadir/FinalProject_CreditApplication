package com.patika.creditapplication.advice;

import com.patika.creditapplication.advice.strategy.ErrorParseStrategy;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.exception.ClientNotFoundException;
import com.patika.creditapplication.exception.CreditStrategyNotFoundException;
import com.patika.creditapplication.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice {
    private final List<ErrorParseStrategy> parseStrategies;

    private ErrorParseStrategy getStrategy(String className){
        return parseStrategies.stream()
                .filter(errorParseStrategy -> errorParseStrategy.getErrorClass().equals(className))
                .findFirst()
                .get();
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
                new Response(400, message),
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
