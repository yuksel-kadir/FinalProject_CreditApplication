package com.patika.creditapplication.smssystem;

import com.patika.creditapplication.enums.Carrier;

public interface SMSSender {
    void sendSMS(Carrier carrier, String message, String phoneNumber);
}
