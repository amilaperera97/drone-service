package com.musalasoft.droneservice.dto.request;

import com.musalasoft.droneservice.dto.DroneBaseInfoDto;
import com.musalasoft.droneservice.dto.DroneModel;
import com.musalasoft.droneservice.dto.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class RegisterDroneDto extends DroneBaseInfoDto {

	private DroneModel model;

	@NotNull
	@Size(max = 100, message = "{validation.name.size.max-weight}")
	private double weightLimit;

	@NotNull
	@Size(min = 0, message = "{validation.name.size.invalid-battery-per}")
	@Size(max = 100, message = "{validation.name.size.invalid-battery-per}")
	private BigDecimal battery;

	private DroneState state;
}
