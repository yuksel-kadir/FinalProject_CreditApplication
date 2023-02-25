package com.patika.creditapplication.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class NewClient {

    @NotBlank (message = "The first name cannot be null or empty.")
    @Pattern(regexp = "^\\p{L}+$", message = "Name can only contain letters")
    private String firstName;

    @NotBlank (message = "The last name cannot be null or empty.")
    @Pattern(regexp = "^\\p{L}+$", message = "Last name can only contain letters")
    private String lastName;

    @NotBlank (message = "The identity number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.")
    private String identityNumber;

    @NotBlank (message = "The phone number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The phone number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of phone number must be 11 characters.")
    private String phoneNumber;

    @NotNull(message = "The date of birth cannot be null.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy/MM/dd")
    private LocalDate dateOfBirth;

    @NotNull(message = "The monthly income cannot be null.")
    @Min(value = 1, message = "The monthly income must be greater than or equal to 1")
    private Float monthlyIncome;

    @NotNull(message = "The collateral cannot be null.")
    @Min(value = 0, message = "The collateral must be greater than or equal to 0")
    private Float collateral;

}
