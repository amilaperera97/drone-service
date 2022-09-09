package com.musalasoft.droneservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DroneBaseInfoDto {
    @NotBlank
    @NotNull
    @Size(max = 100, message = "{validation.name.size.too_long}")
    private String serialNumber;
}
