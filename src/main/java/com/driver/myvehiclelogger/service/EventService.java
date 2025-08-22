package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.web.dto.AddEventRequest;
import com.driver.myvehiclelogger.web.dto.EventDto;

public interface EventService {
    EventDto addEvent(AddEventRequest addEventRequest, Long vehicleId);
}
