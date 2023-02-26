package com.patika.creditapplication.controller;

import com.patika.creditapplication.dto.request.*;
import com.patika.creditapplication.dto.response.CreditApplicationResult;
import com.patika.creditapplication.response.Response;
import com.patika.creditapplication.response.ResponseData;
import com.patika.creditapplication.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
@CrossOrigin
public class ClientController {
    private final ClientService clientService;

    //localhost:8080/creditApplication/registration
    @PostMapping("/creditApplication")
    public ResponseEntity<Response> creditApplicationRegistration(@Valid @RequestBody NewClient newClient) {
        CreditApplicationResult applicationResponse = clientService.processCreditApplication(newClient);
        return new ResponseEntity<>(
                new ResponseData(200, "Credit application registration is complete.", applicationResponse),
                HttpStatus.OK
        );
    }

    @PostMapping("/findCreditApplication")
    public ResponseEntity<Response> findApplication(@Valid @RequestBody CreditApplicationQueryParameters query) {
        CreditApplicationResult result = clientService.findApplicationByIdentityNumberAndDateOfBirth(query.getIdentityNumber(), query.getDateOfBirth());
        return new ResponseEntity<>(
                new ResponseData(200, "Query processed successfully.", result),
                HttpStatus.OK
        );
    }

    @PostMapping("/updateClientInformation/firstName")
    public ResponseEntity<Response> updateClientFirstName(@Valid @RequestBody ClientUpdateNameParam clientParams) {
        String fullName = clientService.updateClientFirstName(clientParams);
        return new ResponseEntity<>(
                new ResponseData(200, "Query processed successfully.", fullName),
                HttpStatus.OK
        );
    }

    @PostMapping("/updateClientInformation/lastName")
    public ResponseEntity<Response> updateClientLastName(@Valid @RequestBody ClientUpdateNameParam clientParams) {
        String fullName = clientService.updateClientLastName(clientParams);
        return new ResponseEntity<>(
                new ResponseData(200, "Query processed successfully.", fullName),
                HttpStatus.OK
        );
    }

    @PostMapping("/updateClientInformation/phoneNumber")
    public ResponseEntity<Response> updateClientPhoneNumber(@Valid @RequestBody ClientUpdatePhoneNumberParam clientParams) {
        String fullName = clientService.updateClientPhoneNumber(clientParams);
        return new ResponseEntity<>(
                new ResponseData(200, "Query processed successfully.", fullName),
                HttpStatus.OK
        );
    }

    //localhost:8080/client/delete
    @DeleteMapping("/deleteClient")
    public ResponseEntity<Response> deleteClient(
            @RequestBody
            @Valid
            @NotBlank(message = "The identity number cannot be null or empty.")
            @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
            @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.") DeleteClient client
    ) {
        clientService.deleteClient(client.getIdentityNumber());
        return new ResponseEntity<>(
                new Response(200, "Client deleted."),
                HttpStatus.OK
        );
    }


}
