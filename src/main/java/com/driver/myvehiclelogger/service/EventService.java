package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.web.dto.AddEventRequest;
import com.driver.myvehiclelogger.web.dto.EventDto;
import com.driver.myvehiclelogger.web.dto.UpdateEventRequest;
import jakarta.validation.Valid;

public interface EventService {
    EventDto addEvent(AddEventRequest addEventRequest, Long vehicleId);

    EventDto updateEvent(UpdateEventRequest updateEventRequest, Long vehicleId, Long eventId);
}
