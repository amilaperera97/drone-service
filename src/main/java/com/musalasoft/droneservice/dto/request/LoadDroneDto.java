package com.musalasoft.droneservice.dto.request;

import com.musalasoft.droneservice.dto.DroneBaseInfoDto;
import com.musalasoft.droneservice.dto.MedicationDataDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class LoadDroneDto extends DroneBaseInfoDto {
    @NotNull
    @NotBlank
    private String source;

    @NotNull
    @NotBlank
    private String destination;

    @NotNull
    @NotBlank
    private List<String> medicationIds;
}
