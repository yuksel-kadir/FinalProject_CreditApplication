package com.patika.creditapplication.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private int statusCode;
    private String Message;
}
