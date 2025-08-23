package com.driver.myvehiclelogger.web;

import com.driver.myvehiclelogger.model.Event;
import com.driver.myvehiclelogger.service.EventService;
import com.driver.myvehiclelogger.service.VehicleService;
import com.driver.myvehiclelogger.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final EventService eventService;

    public VehicleController(VehicleService vehicleService, EventService eventService) {
        this.vehicleService = vehicleService;
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(
            @Valid @RequestBody AddVehicleDto addVehicleRequest,
            UriComponentsBuilder uriBuilder) {

        VehicleDto vehicleDto = vehicleService.addVehicle(addVehicleRequest);

        if (vehicleDto == null) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Registration already exists");
        }

        URI uri = uriBuilder.path("/vehicles/{id}").buildAndExpand(vehicleDto.getId()).toUri();
        return ResponseEntity.created(uri).body(vehicleDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicleByUser() {
        List<VehicleDto> vehicles = vehicleService.findAllVehicleByUser();
        if (vehicles == null) {
            return ResponseEntity.status(HttpStatus.OK).body("No vehicles yet");
        }
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable Long id) {
        VehicleDto vehicleDto = vehicleService.findVehicleById(id);
        if (vehicleDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/{vehicleId}/events")
    public ResponseEntity<EventDto> addEvent(
            @Valid @RequestBody AddEventRequest addEventRequest,
            @PathVariable Long vehicleId,
            UriComponentsBuilder uriBuilder) {

        EventDto eventDto = eventService.addEvent(addEventRequest, vehicleId);

        if (eventDto == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        URI uri = uriBuilder.path("/vehicles/{vehicleId}/events/{id}")
                .buildAndExpand(vehicleId, eventDto.getId()).toUri();

        return ResponseEntity.created(uri).body(eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(
            @Valid @RequestBody UpdateVehicleRequest updateVehicleRequest,
            @PathVariable Long id) {

        VehicleDto vehicleDto = vehicleService.updateVehicle(updateVehicleRequest, id);

        return ResponseEntity.ok(vehicleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{vehicleId}/events/{eventId}")
    public ResponseEntity<?> updateEvent(
            @Valid @RequestBody UpdateEventRequest updateEventRequest,
            @PathVariable Long vehicleId,
            @PathVariable Long eventId) {

        EventDto eventDto = eventService.updateEvent(updateEventRequest, vehicleId, eventId);
        if (eventDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle or event do not exist");
        }
        return ResponseEntity.ok(eventDto);
    }
}
