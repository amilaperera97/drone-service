package com.musalasoft.droneservice;

import com.musalasoft.droneservice.preload.PreLoadInitialData;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
public class DroneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);
	}

}
