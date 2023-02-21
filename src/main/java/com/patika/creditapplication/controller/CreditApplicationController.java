package com.patika.creditapplication.controller;

import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.response.Response;
import com.patika.creditapplication.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/creditApplication")
@AllArgsConstructor
public class CreditApplicationController {
    private final ApplicationService applicationService;
    //@GetMapping("/applicationQuery")

    //localhost:8080/creditApplication/registration
    @PostMapping("/application")
    public ResponseEntity<Response> clientRegistration(@Valid @RequestBody NewClient newClient) {
        Response applicationResponse = applicationService.processCreditApplication(newClient);
        System.out.println("Application Response:" + applicationResponse);
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }
}
