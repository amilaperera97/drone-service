package com.musalasoft.droneservice.dto.request;

import com.musalasoft.droneservice.dto.DroneBaseInfoDto;
import com.musalasoft.droneservice.dto.DroneModel;
import com.musalasoft.droneservice.dto.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneDto extends DroneBaseInfoDto {

    private DroneModel model;

    @NotNull
    @Range(max = 500, message = "{validation.name.size.max-weight}")
    private double weightLimit;

    @NotNull
    @Range(min = 0, max = 100, message = "{validation.name.size.invalid-battery-per}")
    private BigDecimal battery;

    private DroneState state;
}
