package com.patika.creditapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditApplication")
public class CreditApplicationController {

    @GetMapping("/registration")
    public ResponseEntity<String> naturalEntityRegistration() {
        return null;
    }
}
