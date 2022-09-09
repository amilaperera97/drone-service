package com.musalasoft.droneservice.rest;

import com.musalasoft.droneservice.dto.response.ResponseDto;
import com.musalasoft.droneservice.preload.PreLoadInitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.musalasoft.droneservice.util.constance.ControllerConstance.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_V1)
public class PreloadDataController {

    private final PreLoadInitialData preLoadInitialData;

    @GetMapping(value = PRELOAD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<Void> loadAllData() {
        preLoadInitialData.loadData();
        return new ResponseDto<Void>().buildSuccessMsg();
    }
}
