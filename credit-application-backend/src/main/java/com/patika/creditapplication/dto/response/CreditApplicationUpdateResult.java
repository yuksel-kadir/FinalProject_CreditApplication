package com.patika.creditapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationUpdateResult {
    private Float creditLimit;
    private String creditStatus;
}
