package com.musalasoft.droneservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "drone_load")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tracking_id")
    private Integer trackingId;

    @Column(name = "source", columnDefinition = "VARCHAR(30) NOT NULL")
    private String source;

    @Column(name = "destination", columnDefinition = "VARCHAR(30) NOT NULL")
    private String destination;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date_time", nullable = false, updatable = false)
    private Date createdDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_serial_no", referencedColumnName = "serial_no")
    private Drone drone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "loadMedication")
    private List<Medication> medicationList;
}
