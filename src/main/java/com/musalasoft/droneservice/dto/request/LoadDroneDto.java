package com.musalasoft.droneservice.dto.request;

import com.musalasoft.droneservice.dto.DroneBaseInfoDto;
import com.musalasoft.droneservice.dto.MedicationDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
