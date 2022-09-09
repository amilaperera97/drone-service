package com.musalasoft.droneservice.schedulejobs;

import com.musalasoft.droneservice.entity.Drone;
import com.musalasoft.droneservice.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DecimalFormat;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class ScheduledCheckDroneBattery {

    private final DroneRepository droneRepository;

    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {

        List<Drone> droneList = droneRepository.findAll();

        DecimalFormat decFormat = new DecimalFormat("#.#%");

        for (Drone drone : droneList) {
            log.info("Battery level | SerialNumber : {} | Battery Level : {}",drone.getSerialNumber(),decFormat.format(drone.getBattery()));
        }
        Thread.sleep(5000);
    }

}
