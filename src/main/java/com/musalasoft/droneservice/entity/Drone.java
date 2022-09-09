package com.musalasoft.droneservice.entity;

import com.musalasoft.droneservice.dto.DroneModel;
import com.musalasoft.droneservice.dto.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "drone")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drone {

    @Id
    @Column(name = "serial_no", columnDefinition = "VARCHAR(100) NOT NULL")
    private String serialNumber;

    @Column(name = "model", columnDefinition = "VARCHAR(50) NOT NULL")
    private DroneModel model;

    @Column(name = "weight_limit", columnDefinition = "VARCHAR(10) NOT NULL")
    private double weightLimit;

    @Column(name = "battery", precision = 4, scale = 2)
    private BigDecimal battery;

    @Column(name = "drone_state", columnDefinition = "VARCHAR(15) NOT NULL")
    private DroneState state;

}
