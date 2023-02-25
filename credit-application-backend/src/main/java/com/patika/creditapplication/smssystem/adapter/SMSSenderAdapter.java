package com.patika.creditapplication.smssystem.adapter;

import com.patika.creditapplication.enums.Carrier;

public interface SMSSenderAdapter {
    void sendSMSToClient(String message, String phoneNumber);

    Carrier getCarrier();
}
