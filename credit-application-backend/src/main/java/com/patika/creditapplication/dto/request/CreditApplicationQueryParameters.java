package com.patika.creditapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditApplicationQueryParameters {
    @NotBlank(message = "The identity number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.")
    private String identityNumber;

    @NotNull(message = "The date of birth cannot be null.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy/MM/dd")
    private LocalDate dateOfBirth;
}
