package com.patika.creditapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditStatusBase {
    private int statusCode;
    private String statusText;
}
