package com.patika.creditapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationUpdateParams {

    @NotBlank(message = "The identity number cannot be null or empty.")
    @Pattern(regexp = "^\\d+$", message = "The Identity number must contain only numbers")
    @Size(min = 11, max = 11, message = "The length of identity number must be 11 characters.")
    private String identityNumber;

    @NotNull(message = "The monthly income cannot be null.")
    @Min(value = 1, message = "The monthly income must be greater than or equal to 1")
    private Float monthlyIncome;

    @NotNull(message = "The collateral cannot be null.")
    @Min(value = 0, message = "The collateral must be greater than or equal to 0")
    private Float collateral;

}
