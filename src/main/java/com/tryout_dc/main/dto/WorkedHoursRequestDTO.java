package com.tryout_dc.main.dto;

import lombok.Getter;
import lombok.Setter;

public class WorkedHoursRequestDTO {

    @Getter
    @Setter
    private Long employeeId;

    @Getter
    @Setter
    private String startDate;

    @Getter
    @Setter
    private String endDate;

}
