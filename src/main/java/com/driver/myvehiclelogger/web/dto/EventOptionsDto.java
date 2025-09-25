package com.driver.myvehiclelogger.web.dto;

import com.driver.myvehiclelogger.model.enums.EventCategory;
import lombok.Data;

import java.util.List;

@Data
public class EventOptionsDto {

    private List<EventCategory> eventCategories;

}
