package com.patika.creditapplication.response;

import lombok.Getter;

@Getter
public class ResponseData extends Response {
    private final Object resultData;

    public ResponseData(int statusCode, String Message, Object resultData) {
        super(statusCode, Message);
        this.resultData = resultData;
    }
}
