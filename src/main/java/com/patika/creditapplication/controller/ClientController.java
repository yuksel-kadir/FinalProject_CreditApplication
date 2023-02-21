package com.patika.creditapplication.controller;

import com.patika.creditapplication.dto.DeleteClient;
import com.patika.creditapplication.response.Response;
import com.patika.creditapplication.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    //localhost:8080/client/delete/{identity}
    @DeleteMapping("/delete")
    public Response deleteClient(
            @RequestBody
            @Valid
            @NotBlank(message = "The identity number cannot be null or empty.")
            @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
            @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.") DeleteClient client
    ) {
        clientService.deleteClient(client.getIdentity());
        System.out.println("Deleted: " + client.getIdentity());
        return new Response(200, "Client deleted.");
    }
}
