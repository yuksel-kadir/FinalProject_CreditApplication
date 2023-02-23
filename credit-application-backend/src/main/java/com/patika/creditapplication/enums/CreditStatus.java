package com.patika.creditapplication.enums;

public enum CreditStatus {
    REJECTED(0),
    APPROVED(1);
    final int value;

    CreditStatus(int value) {
        this.value = value;
    }
}
