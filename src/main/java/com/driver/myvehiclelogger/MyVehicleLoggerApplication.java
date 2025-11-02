package com.driver.myvehiclelogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyVehicleLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyVehicleLoggerApplication.class, args);
    }

}
