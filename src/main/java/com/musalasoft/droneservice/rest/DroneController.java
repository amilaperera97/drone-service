package com.musalasoft.droneservice.rest;

import com.musalasoft.droneservice.dto.DroneState;
import com.musalasoft.droneservice.dto.MedicationDataDto;
import com.musalasoft.droneservice.dto.request.LoadDroneDto;
import com.musalasoft.droneservice.dto.request.DroneDto;
import com.musalasoft.droneservice.dto.response.ResponseDto;
import com.musalasoft.droneservice.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.musalasoft.droneservice.util.constance.ControllerConstance.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_V1)
public class DroneController {

    private final DroneService droneService;

    @PostMapping(value = REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<DroneDto> register(@Valid @NotNull @RequestBody DroneDto registerDrone) {
        registerDrone.setState(DroneState.IDLE);
        return new ResponseDto<DroneDto>()
                .buildSuccessMsgWithData(droneService.register(registerDrone));
    }

    @PostMapping(value = LOAD_MEDICATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<LoadDroneDto> loadMedication(@Valid @NotNull @RequestBody LoadDroneDto loadMedicationDrone) {
        DroneDto droneDto = droneService.findDrone(loadMedicationDrone.getSerialNumber());
        if (droneDto.getState() == DroneState.IDLE) {
            return new ResponseDto<LoadDroneDto>().buildSuccessMsgWithData(droneService.loadMedication(loadMedicationDrone));
        }
        return new ResponseDto<LoadDroneDto>().buildFailureMsg("Drone is in" + droneDto.getState().name(), new Exception());
    }

    @GetMapping(value = FIND_MED_INFO_BY_SERIAL_NUMBER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<List<MedicationDataDto>> findMedicationInfoBySerialNumber(@PathVariable("serial-number") String serialNumber) {
        return new ResponseDto<List<MedicationDataDto>>().buildSuccessMsgWithData(droneService.findMedicationInfoBySerialNumber(serialNumber));
    }

    @GetMapping(value = AVAILABLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<List<DroneDto>> findAvailableDrones() {
        return new ResponseDto<List<DroneDto>>().buildSuccessMsgWithData(droneService.findDroneByState(DroneState.IDLE));
    }

    @GetMapping(value = BATTERY_INFO_BY_SERIAL_NUMBER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<DroneDto> checkBatteryLevelBySerialNumber(@PathVariable("serial-number") String serialNumber) {
        return new ResponseDto<DroneDto>().buildSuccessMsgWithData(droneService.findDrone(serialNumber));
    }
}
