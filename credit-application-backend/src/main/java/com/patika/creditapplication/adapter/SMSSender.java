package com.patika.creditapplication.adapter;

import com.patika.creditapplication.enums.Carrier;

public interface SMSSender {
    void sendSMS(Carrier carrier, String message, String phoneNumber);
}
