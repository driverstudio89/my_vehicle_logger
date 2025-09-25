package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.web.dto.AddEventRequest;
import com.driver.myvehiclelogger.web.dto.EventDto;
import com.driver.myvehiclelogger.web.dto.EventOptionsDto;
import com.driver.myvehiclelogger.web.dto.UpdateEventRequest;

import java.util.List;

public interface EventService {
    EventDto addEvent(AddEventRequest addEventRequest, Long vehicleId);

    EventDto updateEvent(UpdateEventRequest updateEventRequest, Long vehicleId, Long eventId);

    void deleteEvent(Long vehicleId, Long eventId);

    List<EventDto> getEventByVehicleId(Long vehicleId);

    EventOptionsDto getEventOptions();
}
