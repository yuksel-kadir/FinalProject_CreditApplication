package com.patika.creditapplication.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ClientUpdatePhoneNumberParam {
    @NotBlank(message = "The identity number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.")
    private String identityNumber;

    @NotBlank (message = "The phone number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The phone number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of phone number must be 11 characters.")
    private String phoneNumber;
}
