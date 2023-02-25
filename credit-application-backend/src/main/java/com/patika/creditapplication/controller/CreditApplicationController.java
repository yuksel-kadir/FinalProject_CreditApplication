package com.patika.creditapplication.controller;

import com.patika.creditapplication.dto.ClientInformation;
import com.patika.creditapplication.dto.request.CreditApplicationUpdateParams;
import com.patika.creditapplication.dto.response.CreditApplicationUpdateResult;
import com.patika.creditapplication.response.Response;
import com.patika.creditapplication.response.ResponseData;
import com.patika.creditapplication.service.ApplicationService;
import com.patika.creditapplication.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/creditApplication")
@AllArgsConstructor
@CrossOrigin
public class CreditApplicationController {
    private final ApplicationService applicationService;
    private final ClientService clientService;
    //localhost:8080/creditApplication/findApplication

    @PostMapping("/updateCreditApplication")
    public ResponseEntity<Response> updateCreditApplication(@Valid @RequestBody CreditApplicationUpdateParams params){
        ClientInformation clientInformation = clientService.getClientInformation(params.getIdentityNumber());
        clientInformation.setNewMonthlyIncome(params.getMonthlyIncome());
        clientInformation.setNewCollateral(params.getCollateral());
        CreditApplicationUpdateResult updateResult = applicationService.updateCreditApplication(clientInformation);
        return new ResponseEntity<>(
                new ResponseData(
                        200,
                        "Credit Application is updated.",
                        updateResult
                ),
                HttpStatus.OK
        );
    }

}
