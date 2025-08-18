package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.data.UserRepository;
import com.driver.myvehiclelogger.data.VehicleRepository;
import com.driver.myvehiclelogger.mapper.VehicleMapper;
import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.model.Vehicle;
import com.driver.myvehiclelogger.model.enums.Category;
import com.driver.myvehiclelogger.model.enums.Color;
import com.driver.myvehiclelogger.model.enums.Engine;
import com.driver.myvehiclelogger.service.VehicleService;
import com.driver.myvehiclelogger.service.auth.Jwt;
import com.driver.myvehiclelogger.service.auth.JwtService;
import com.driver.myvehiclelogger.service.auth.UserAuthService;
import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserAuthService userAuthService;


    @Override
    public VehicleDto addVehicle(AddVehicleDto addVehicleDto) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehiclesByRegistration(addVehicleDto.getRegistration());
        if (optionalVehicle.isEmpty()) {
            Vehicle vehicle = mappingVehicle(addVehicleDto);
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            return vehicleMapper.toVehicleDto(savedVehicle);
        }
        return null;
    }

    private Vehicle mappingVehicle(AddVehicleDto addVehicleDto) {
        Vehicle vehicle = vehicleMapper.toEntity(addVehicleDto);
        User currentUser = userAuthService.getCurrentUser();
        vehicle.setCreated(now());
        vehicle.setUpdated(now());
        System.out.println();
        vehicle.setColor(Color.valueOf(addVehicleDto.getColor().toUpperCase()));
        vehicle.setEngine(Engine.valueOf(addVehicleDto.getEngine().toUpperCase()));
        vehicle.setCategory(Category.valueOf(addVehicleDto.getCategory().toUpperCase()));
        vehicle.setOwner(currentUser);
        return vehicle;
    }
}
