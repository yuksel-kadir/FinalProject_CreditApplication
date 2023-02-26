package com.patika.creditapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClient {
    @NotBlank(message = "The identity number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.")
    private String identityNumber;
}
