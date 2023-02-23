package com.patika.creditapplication.adapter;

import com.patika.creditapplication.enums.Carrier;

public interface SMSSenderAdapter {
    void sendSMSToClient(String message, String phoneNumber);

    Carrier getCarrier();
}
