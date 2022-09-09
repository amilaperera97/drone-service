package com.musalasoft.droneservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalDelivery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "delivery_time", columnDefinition = "TIMESTAMP NOT NULL")
	private LocalDateTime deliveryTime;

	@OneToOne(targetEntity = LoadMedication.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_tracking_id", referencedColumnName = "tracking_id")
	private LoadMedication loadMedication;


}
