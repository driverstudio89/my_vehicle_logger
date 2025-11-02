package com.driver.myvehiclelogger.web.dto;

import com.driver.myvehiclelogger.model.enums.EventCategory;
import lombok.Data;

@Data
public class EventDto {

    private Long id;

    private String name;

    private String description;

    private Integer kilometers;

    private Long vehicleId;

    private EventCategory eventCategory;

    private String startDate;

    private String endDate;

    private Boolean notification;
}
