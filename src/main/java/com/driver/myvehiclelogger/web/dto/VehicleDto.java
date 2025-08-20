package com.driver.myvehiclelogger.web.dto;

import com.driver.myvehiclelogger.model.enums.Category;
import com.driver.myvehiclelogger.model.enums.Color;
import com.driver.myvehiclelogger.model.enums.Engine;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleDto {

    private Long id;

    private String make;

    private String model;

    private Integer year;

    private String registration;

    private Color color;

    private Category category;

    private Engine engine;

    private Integer lastKilometers;

    private String description;

    private Long ownerId;

    private LocalDate created;

    private LocalDate updated;
}
