package com.musalasoft.droneservice.service;

import com.musalasoft.droneservice.dto.DroneState;
import com.musalasoft.droneservice.dto.MedicationDataDto;
import com.musalasoft.droneservice.dto.request.LoadDroneDto;
import com.musalasoft.droneservice.dto.request.DroneDto;

import java.util.List;

public interface DroneService {
    DroneDto register(DroneDto registerDrone);
    LoadDroneDto loadMedication(LoadDroneDto loadMedicationDrone);

    List<MedicationDataDto> findMedicationInfoBySerialNumber(String serialNumber);

    List<DroneDto> findDroneByState(DroneState droneState);

    DroneDto findDrone(String serialNumber);
}
