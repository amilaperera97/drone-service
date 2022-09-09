package com.musalasoft.droneservice.rest;

import com.musalasoft.droneservice.dto.MedicationDataDto;
import com.musalasoft.droneservice.dto.response.ResponseDto;
import com.musalasoft.droneservice.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.musalasoft.droneservice.util.constance.ControllerConstance.API_V1;
import static com.musalasoft.droneservice.util.constance.ControllerConstance.MEDICATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_V1)
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping(value = MEDICATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<MedicationDataDto> save(@RequestBody MedicationDataDto medicationData) {
        return new ResponseDto<MedicationDataDto>().buildSuccessMsgWithData(medicationService.save(medicationData));
    }
}
