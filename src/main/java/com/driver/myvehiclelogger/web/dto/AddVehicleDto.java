package com.driver.myvehiclelogger.web.dto;

import com.driver.myvehiclelogger.model.enums.Category;
import com.driver.myvehiclelogger.model.enums.Color;
import com.driver.myvehiclelogger.model.enums.Engine;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddVehicleDto {

    @NotBlank(message = "Make is required")
    @Size(min = 1, max = 30, message = "Make must be between 1 and 30 characters")
    private String make;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 30, message = "Model must be between 1 and 30 characters")
    private String model;

    @NotNull(message = "Year is required")
    @Positive(message = "Year must be positive number")
    @Min(1900)
    @Max(2100)
    private Integer year;

    @NotBlank(message = "Registration is required")
    @Size(min = 4, max = 12)
    private String registration;

    private String color;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Engine is required")
    private String engine;

    @NotNull(message = "Kilometers is required")
    @Max(1000000)
    @PositiveOrZero
    private Integer lastKilometers;

    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    private String description;

}
