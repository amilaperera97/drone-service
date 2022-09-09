package com.musalasoft.droneservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Builder
public class MedicationDataDto {

    @Pattern(regexp = "(?=.*[A-Z])[\\p{Punct}A-Z0-9a-z ]", message = "{validation.name.allowed.invalid-name}")
    private String name;
    private double weight;
    @Pattern(regexp = "(?=.*[A-Z])[\\p{Punct}A-Z0-9 ]", message = "{validation.name.allowed.invalid-code}")
    private String code;
    private String image;
}
