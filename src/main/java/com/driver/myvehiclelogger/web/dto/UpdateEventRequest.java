package com.driver.myvehiclelogger.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEventRequest {

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(min = 2, max = 2000)
    private String description;

    @Max(1000000)
    @PositiveOrZero
    private Integer kilometers;

    @NotBlank(message = "Event category is required")
    private String eventCategory;

    private String startDate;

    private String endDate;

    private Boolean notification;

}
