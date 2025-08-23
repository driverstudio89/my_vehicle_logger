package com.driver.myvehiclelogger.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateVehicleRequest {

    @Size(min = 1, max = 30, message = "Make must be between 1 and 30 characters")
    private String make;

    @Size(min = 1, max = 30, message = "Model must be between 1 and 30 characters")
    private String model;

    @Positive(message = "Year must be positive number")
    @Min(1900)
    @Max(2100)
    private Integer year;

    @Size(min = 4, max = 12)
    private String registration;

    private String color;

    private String category;

    private String engine;

    @Max(1000000)
    @PositiveOrZero
    private Integer lastKilometers;

    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    private String description;

}
