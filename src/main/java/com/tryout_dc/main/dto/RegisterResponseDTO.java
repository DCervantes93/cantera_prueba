package com.tryout_dc.main.dto;

import lombok.Getter;
import lombok.Setter;

public class RegisterResponseDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private boolean success;

    @Getter
    @Setter
    private String message;

}
