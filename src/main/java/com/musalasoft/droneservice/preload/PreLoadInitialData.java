package com.musalasoft.droneservice.preload;

import com.musalasoft.droneservice.dto.DroneModel;
import com.musalasoft.droneservice.dto.DroneState;
import com.musalasoft.droneservice.entity.Drone;
import com.musalasoft.droneservice.entity.Medication;
import com.musalasoft.droneservice.repository.DroneRepository;
import com.musalasoft.droneservice.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PreLoadInitialData {
    private final MedicationRepository medicationRepository;
    private final DroneRepository droneRepository;

    @PrePersist
    public void loadData() {
        medicationRepository.saveAll(medicationData(10));
        droneRepository.saveAll(medicationData());
    }

    private List<Medication> medicationData(int noOfRecords) {
        List<Medication> medicationList = new ArrayList<>();

        while (noOfRecords <= 10 && 0 < noOfRecords) {
            Medication medication = Medication.builder()
                    .code(generateString().toUpperCase().substring(0, 25))
                    .name(generateString().toUpperCase().substring(0, 25))
                    .image("https://picsum.photos/seed/picsum/200/300")
                    .weight(5 * noOfRecords)
                    .build();
            medicationList.add(medication);
            noOfRecords--;
        }
        return medicationList;
    }

    //Drone data
    private List<Drone> medicationData() {
        List<Drone> droneList = new ArrayList<>();
        droneList.add(new Drone(generateString(), DroneModel.HEAVY_WEIGHT, 300, new BigDecimal(40), DroneState.IDLE));
        droneList.add(new Drone(generateString(), DroneModel.CRUISER_WEIGHT, 300, new BigDecimal(50), DroneState.IDLE));
        droneList.add(new Drone(generateString(), DroneModel.LIGHT_WEIGHT, 200, new BigDecimal(80), DroneState.IDLE));
        droneList.add(new Drone(generateString(), DroneModel.MIDDLE_WEIGHT, 100, new BigDecimal(10), DroneState.IDLE));
        droneList.add(new Drone(generateString(), DroneModel.HEAVY_WEIGHT, 500, new BigDecimal(60), DroneState.IDLE));
        return droneList;
    }

    private static String generateString() {
        return UUID.randomUUID().toString();
    }
}
