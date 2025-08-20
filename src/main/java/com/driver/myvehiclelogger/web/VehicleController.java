package com.driver.myvehiclelogger.web;

import com.driver.myvehiclelogger.service.VehicleService;
import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
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

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<?> addVehicle(
            @Valid @RequestBody AddVehicleDto addVehicleDto,
            UriComponentsBuilder uriBuilder) {

        VehicleDto vehicleDto = vehicleService.addVehicle(addVehicleDto);

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
}
