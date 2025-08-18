package com.driver.myvehiclelogger.web;

import com.driver.myvehiclelogger.service.VehicleService;
import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
}
