package com.musalasoft.droneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DroneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);
	}

}
