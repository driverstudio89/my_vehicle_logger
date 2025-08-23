package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.data.EventRepository;
import com.driver.myvehiclelogger.data.VehicleRepository;
import com.driver.myvehiclelogger.mapper.EventMapper;
import com.driver.myvehiclelogger.model.Event;
import com.driver.myvehiclelogger.model.Vehicle;
import com.driver.myvehiclelogger.service.EventService;
import com.driver.myvehiclelogger.service.auth.UserAuthService;
import com.driver.myvehiclelogger.web.dto.AddEventRequest;
import com.driver.myvehiclelogger.web.dto.EventDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final VehicleRepository vehicleRepository;
    private final UserAuthService userAuthService;

    @Override
    @Transactional
    public EventDto addEvent(AddEventRequest addEventRequest, Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId).orElse(null);
        if (vehicle.getOwnerId() != userAuthService.getCurrentUser().getId()) {
            throw new AccessDeniedException("Access denied");
        }
        Event event = eventMapper.toEntity(addEventRequest);
        event.setVehicle(vehicle);
        Event savedEvent = eventRepository.save(event);
        vehicle.getEvents().add(savedEvent);
        vehicleRepository.save(vehicle);
        return eventMapper.toDto(savedEvent);
    }
}
