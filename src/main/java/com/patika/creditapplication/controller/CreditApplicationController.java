package com.patika.creditapplication.controller;

import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/creditApplication")
@AllArgsConstructor
public class CreditApplicationController {
    private final ClientService clientService;
    //@GetMapping("/applicationQuery")

    //localhost:8080/creditApplication/registration
    @PostMapping("/application")
    public ResponseEntity<NewClient> clientRegistration(@Valid @RequestBody NewClient newClient) {
        System.out.println("New Client:" + newClient);
        clientService.RegisterClient(newClient);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }
}
