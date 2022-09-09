package com.musalasoft.droneservice.repository;

import com.musalasoft.droneservice.entity.Drone;
import com.musalasoft.droneservice.entity.LoadMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoadMedicationRepository extends JpaRepository<LoadMedication, String> {
    Optional<LoadMedication> findLoadMedicationByDrone(Drone drone);
}
