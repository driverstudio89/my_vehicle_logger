package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.data.EventRepository;
import com.driver.myvehiclelogger.data.VehicleRepository;
import com.driver.myvehiclelogger.mapper.EventMapper;
import com.driver.myvehiclelogger.model.Event;
import com.driver.myvehiclelogger.model.Vehicle;
import com.driver.myvehiclelogger.model.enums.EventCategory;
import com.driver.myvehiclelogger.service.EventService;
import com.driver.myvehiclelogger.service.auth.UserAuthService;
import com.driver.myvehiclelogger.web.dto.AddEventRequest;
import com.driver.myvehiclelogger.web.dto.EventDto;
import com.driver.myvehiclelogger.web.dto.EventOptionsDto;
import com.driver.myvehiclelogger.web.dto.UpdateEventRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        if (vehicle == null) {
            return null;
        }
        if (!vehicle.getOwnerId().equals(userAuthService.getCurrentUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        Event event = eventMapper.toEntity(addEventRequest);
        event.setEventCategory(EventCategory.valueOf(addEventRequest.getEventCategory().toUpperCase()));
        event.setVehicle(vehicle);
        Event savedEvent = eventRepository.save(event);
        vehicle.getEvents().add(savedEvent);
        if (vehicle.getLastKilometers() < event.getKilometers()) {
            vehicle.setLastKilometers(event.getKilometers());
        }
        vehicleRepository.save(vehicle);
        return eventMapper.toDto(savedEvent);
    }

    @Override
    public EventDto updateEvent(UpdateEventRequest updateEventRequest, Long vehicleId, Long eventId) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId).orElse(null);
        if (vehicle == null) {
            return null;
        }
        if (!vehicle.getOwnerId().equals(userAuthService.getCurrentUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            return null;
        }
        if (!event.getVehicle().getId().equals(vehicleId)) {
            throw new AccessDeniedException("Access denied");
        }

        mappingUpdate(updateEventRequest, event);
        eventRepository.save(event);
        if (vehicle.getLastKilometers() < event.getKilometers()) {
            vehicle.setLastKilometers(event.getKilometers());
            vehicleRepository.save(vehicle);
        }
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Long vehicleId, Long eventId) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId).orElse(null);
        if (vehicle == null) {
            return;
        }
        if (!vehicle.getOwnerId().equals(userAuthService.getCurrentUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            return;
        }
        if (!event.getVehicle().getId().equals(vehicleId)) {
            throw new AccessDeniedException("Access denied");
        }
        vehicle.getEvents().remove(event);
        eventRepository.delete(event);
    }

    @Override
    public List<EventDto> getEventByVehicleId(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId).orElse(null);
        if (vehicle == null) {
            return null;
        }
        List<Event> eventList = eventRepository.findAllByVehicleOrderByKilometersDesc(vehicle);
        return eventList.stream().map(eventMapper::toDto).toList();
    }

    @Override
    public EventOptionsDto getEventOptions() {
        List<EventCategory> eventCategories = Arrays.stream(EventCategory.values()).toList();
        EventOptionsDto eventOptionsDto = new EventOptionsDto();
        eventOptionsDto.setEventCategories(eventCategories);
        return eventOptionsDto;
    }

    private static void mappingUpdate(UpdateEventRequest updateEventRequest, Event event) {
        if (updateEventRequest.getName() != null) {
            event.setName(updateEventRequest.getName());
        }

        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }

        if (updateEventRequest.getKilometers() != null) {
            event.setKilometers(updateEventRequest.getKilometers());
        }

        if (updateEventRequest.getEventCategory() != null) {
            event.setEventCategory(EventCategory.valueOf(updateEventRequest.getEventCategory().toUpperCase()));
        }

        if (updateEventRequest.getStartDate() != null) {
            event.setStartDate(LocalDate.parse(updateEventRequest.getStartDate()));
        }

        if (updateEventRequest.getEndDate() != null) {
            event.setEndDate(LocalDate.parse(updateEventRequest.getEndDate()));
        }

        event.setNotification(updateEventRequest.getNotification());

    }
}
