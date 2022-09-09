package com.musalasoft.droneservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "medication")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medication {
    @Id
    @Column(name = "code", columnDefinition = "VARCHAR(30) NOT NULL")
    private String code;

    @Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL")
    private String name;

    @Column(name = "weight", columnDefinition = "VARCHAR(10) NOT NULL")
    private double weight;

    @Column(name = "medication_image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_load_medication_id")
    @JsonBackReference
    private LoadMedication loadMedication;
}
