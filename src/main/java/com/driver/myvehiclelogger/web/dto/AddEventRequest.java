package com.driver.myvehiclelogger.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddEventRequest {

    @NotNull(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(min = 2, max = 2000, message = "Description must be between 2 and 2000 characters")
    private String description;

    @NotNull(message = "Kilometers is required")
    @Max(1000000)
    @PositiveOrZero
    private Integer kilometers;

    private String startDate;

    private String endDate;
}
