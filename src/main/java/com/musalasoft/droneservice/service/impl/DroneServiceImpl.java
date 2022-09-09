package com.musalasoft.droneservice.service.impl;

import com.musalasoft.droneservice.dto.DroneState;
import com.musalasoft.droneservice.dto.MedicationDataDto;
import com.musalasoft.droneservice.dto.request.DeliveryDroneDto;
import com.musalasoft.droneservice.dto.request.LoadDroneDto;
import com.musalasoft.droneservice.dto.request.DroneDto;
import com.musalasoft.droneservice.entity.Drone;
import com.musalasoft.droneservice.entity.LoadMedication;
import com.musalasoft.droneservice.entity.MedicalDelivery;
import com.musalasoft.droneservice.entity.Medication;
import com.musalasoft.droneservice.exception.custom.RecordNotFoundException;
import com.musalasoft.droneservice.repository.DroneRepository;
import com.musalasoft.droneservice.repository.LoadMedicationRepository;
import com.musalasoft.droneservice.repository.MedicationDeliveryRepository;
import com.musalasoft.droneservice.repository.MedicationRepository;
import com.musalasoft.droneservice.service.DroneService;
import com.musalasoft.droneservice.util.converter.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final LoadMedicationRepository loadMedicationRepository;
    private final MedicationRepository medicationRepository;
    private final EntityConverter entityConverter;

    private final MedicationDeliveryRepository medicationDeliveryRepository;

    @Override
    public DroneDto register(DroneDto registerDrone) {
        log.info("Register Drone Service ID : {}", registerDrone.getSerialNumber());
        Drone drone = entityConverter.convert(registerDrone, Drone.class);
        Drone savedData = droneRepository.save(drone);
        return entityConverter.convert(savedData, DroneDto.class);
    }

    private Drone updateDroneState(String serialNumber, DroneState droneState) {
        Optional<Drone> optionalDrone = droneRepository.findById(serialNumber);
        optionalDrone.orElseThrow(() -> new RecordNotFoundException(HttpStatus.BAD_REQUEST, "Invalid Drone ID"));
        optionalDrone.get().setState(droneState);
        return droneRepository.save(optionalDrone.get());
    }

    @Override
    public LoadDroneDto loadMedication(LoadDroneDto loadMedicationDrone) {
        log.info("Update Drone State {}", loadMedicationDrone.getSerialNumber());
        Drone drone = updateDroneState(loadMedicationDrone.getSerialNumber(), DroneState.LOADING);
        log.info("Load medication to Drone : {}", loadMedicationDrone.getSerialNumber());
        //Validate medication list DB
        List<Medication> fetchedMedicationList = medicationRepository.findAllById(loadMedicationDrone.getMedicationIds());
        if (fetchedMedicationList.isEmpty()) {
            updateDroneState(loadMedicationDrone.getSerialNumber(), DroneState.IDLE);
            throw new RuntimeException("Invalid medication id/s");
        }
        LoadMedication loadMedication = entityConverter.convert(loadMedicationDrone, LoadMedication.class);
        loadMedication.setDrone(drone);
        List<Medication> finalMedicationList = validateDroneWeight(drone, fetchedMedicationList, loadMedication);

        if (finalMedicationList.isEmpty()) {
            updateDroneState(loadMedicationDrone.getSerialNumber(), DroneState.IDLE);
            throw new RuntimeException("Provided Medication does not match to the drone max weight");
        }

        medicationRepository.saveAll(finalMedicationList);

        updateDroneState(loadMedicationDrone.getSerialNumber(), DroneState.LOADED);
        //Return with updated data
        LoadDroneDto response = loadMedicationDrone;
        response.setMedicationIds(new ArrayList<>());
        finalMedicationList.forEach(medication -> response.getMedicationIds().add(medication.getCode()));
        response.setSerialNumber(drone.getSerialNumber());
        return response;
    }

    private List<Medication> validateDroneWeight(Drone drone, List<Medication> fetchedMedicationList, LoadMedication loadMedication) {
        List<Medication> finalList = new ArrayList<>();
        double totalWeight = 0;
        List<Medication> medicationSortedList = fetchedMedicationList.stream()
                .sorted(Comparator.comparingDouble(Medication::getWeight)).collect(Collectors.toList());

        for (Medication medication : medicationSortedList) {
            totalWeight += medication.getWeight();
            if (totalWeight <= drone.getWeightLimit()) {
                medication.setLoadMedication(loadMedication);
                finalList.add(medication);
            } else {
                break;
            }
        }
        return finalList;
    }

    @Override
    public List<MedicationDataDto> findMedicationInfoBySerialNumber(String serialNumber) {
        Optional<Drone> optionalDrone = droneRepository.findById(serialNumber);
        optionalDrone.orElseThrow(() -> new RuntimeException("Invalid Drone Id!"));

        Optional<LoadMedication> optionalLoadMedication = loadMedicationRepository.findLoadMedicationByDrone(optionalDrone.get());
        optionalLoadMedication.orElseThrow(() -> new RuntimeException("Drone has no medication info!"));

        List<MedicationDataDto> medicationData = entityConverter.convert(optionalLoadMedication.get().getMedicationList(), MedicationDataDto.class);

        return medicationData;
    }

    @Override
    public List<DroneDto> findDroneByState(DroneState droneState) {
        List<Drone> droneList = droneRepository.findAllByState(droneState);
        return entityConverter.convert(droneList, DroneDto.class);
    }

    @Override
    public DroneDto findDrone(String serialNumber) throws RecordNotFoundException {
        Optional<Drone> optionalDrone = droneRepository.findById(serialNumber);
        optionalDrone.orElseThrow(() -> new RuntimeException("Invalid Drone Id!"));
        return entityConverter.convert(optionalDrone.get(), DroneDto.class);
    }

    @Override
    public DeliveryDroneDto deliverMedication(String serialNumber) {
        updateDroneState(serialNumber, DroneState.DELIVERING);

        Optional<Drone> optionalDrone = droneRepository.findById(serialNumber);
        Optional<LoadMedication> optionalLoadMedication = loadMedicationRepository.findLoadMedicationByDrone(optionalDrone.get());

        MedicalDelivery medicalDelivery = MedicalDelivery.builder()
                .deliveryTime(LocalDateTime.now())
                .loadMedication(optionalLoadMedication.get())
                .build();

        MedicalDelivery savedDate = medicationDeliveryRepository.save(medicalDelivery);
        if (savedDate != null) {
            updateDroneState(serialNumber, DroneState.DELIVERED);
        } else {
            updateDroneState(serialNumber, DroneState.LOADED);
        }
        DeliveryDroneDto deliveryDroneDto = new DeliveryDroneDto();
        deliveryDroneDto.setSerialNumber(serialNumber);
        
        return deliveryDroneDto;
    }


}
