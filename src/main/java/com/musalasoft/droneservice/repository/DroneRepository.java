package com.musalasoft.droneservice.repository;

import com.musalasoft.droneservice.dto.DroneState;
import com.musalasoft.droneservice.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findAllByState(DroneState state);

    Optional<Drone> findDroneByBattery(String serialNumber);
}
