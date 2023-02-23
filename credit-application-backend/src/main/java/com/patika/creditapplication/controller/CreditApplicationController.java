package com.patika.creditapplication.controller;

import com.patika.creditapplication.dto.CreditApplicationQuery;
import com.patika.creditapplication.dto.CreditApplicationResult;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.response.Response;
import com.patika.creditapplication.response.ResponseData;
import com.patika.creditapplication.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/creditApplication")
@AllArgsConstructor
public class CreditApplicationController {
    private final ApplicationService applicationService;

    //localhost:8080/creditApplication/findApplication
    @GetMapping("/findApplication")
    public ResponseEntity<Response> findApplication(@Valid @RequestBody CreditApplicationQuery query) {
        CreditApplicationResult result = applicationService.findApplicationByIdentityNumberAndDateOfBirth(query.getIdentityNumber(), query.getDateOfBirth());
        return new ResponseEntity<>(
                new ResponseData(200, "Query processed successfully.", result),
                HttpStatus.FOUND
        );
    }

    //localhost:8080/creditApplication/registration
    @PostMapping("/application")
    public ResponseEntity<Response> creditApplicationRegistration(@Valid @RequestBody NewClient newClient) {
        CreditApplicationResult applicationResponse = applicationService.processCreditApplication(newClient);
        return new ResponseEntity<>(
                new ResponseData(200, "Credit application registration is complete.", applicationResponse),
                HttpStatus.OK
        );
    }
}
