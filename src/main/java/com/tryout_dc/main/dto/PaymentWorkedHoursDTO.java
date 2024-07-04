package com.tryout_dc.main.dto;

import lombok.Getter;
import lombok.Setter;

public class PaymentWorkedHoursDTO {

    @Getter
    @Setter
    private Integer payment;

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private String message;

}
