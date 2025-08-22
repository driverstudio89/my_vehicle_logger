package com.driver.myvehiclelogger.web.dto;

import lombok.Data;

@Data
public class EventDto {

    private Long id;

    private String name;

    private String description;

    private Integer kilometers;

    private Long vehicleId;

    private String startDate;

    private String endDate;
}
