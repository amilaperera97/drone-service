package com.musalasoft.droneservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

	@Column(name = "created_date", columnDefinition = "VARCHAR(30) NOT NULL")
	private LocalDateTime createdDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_serial_no", referencedColumnName = "serial_no")
	private Drone drone;

	@OneToMany(mappedBy = "code")
	private List<Medication> medicationList;
}
