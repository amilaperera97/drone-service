package com.musalasoft.droneservice.repository;

import com.musalasoft.droneservice.entity.MedicalDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationDeliveryRepository extends JpaRepository<MedicalDelivery, Integer> {
}
