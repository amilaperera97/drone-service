package com.musalasoft.droneservice.service.impl;

import com.musalasoft.droneservice.dto.MedicationDataDto;
import com.musalasoft.droneservice.entity.Medication;
import com.musalasoft.droneservice.repository.MedicationRepository;
import com.musalasoft.droneservice.service.MedicationService;
import com.musalasoft.droneservice.util.converter.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final EntityConverter entityConverter;

    @Override
    public MedicationDataDto save(MedicationDataDto medicationData) {
        Medication medication = entityConverter.convert(medicationData, Medication.class);
        Medication savedData = medicationRepository.save(medication);
        return entityConverter.convert(savedData, MedicationDataDto.class);
    }
}
