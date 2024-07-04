package com.tryout_dc.main.dto;

import lombok.Getter;
import lombok.Setter;

public class WorkedHoursResponseDTO {

    @Getter
    @Setter
    private Integer totalWorkedHours;

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private String message;

}
