package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.data.VehicleRepository;
import com.driver.myvehiclelogger.mapper.VehicleMapper;
import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.model.Vehicle;
import com.driver.myvehiclelogger.model.enums.Category;
import com.driver.myvehiclelogger.model.enums.Color;
import com.driver.myvehiclelogger.model.enums.Engine;
import com.driver.myvehiclelogger.service.VehicleService;
import com.driver.myvehiclelogger.service.auth.UserAuthService;
import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.LocalDate.now;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
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

    @Override
    public List<VehicleDto> findAllVehicleByUser() {
        User currentUser = userAuthService.getCurrentUser();
        List<Vehicle> vehicles = vehicleRepository.findAllVehicleByUser(currentUser);
        if (vehicles.isEmpty()) {
            return null;
        }
        return vehicles.stream().map(vehicleMapper::toVehicleDto).toList();
    }

    @Override
    public VehicleDto findVehicleById(Long id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleById(id);
        if (optionalVehicle.isEmpty()) {
            return null;
        }
        Vehicle vehicle = optionalVehicle.get();
        if (vehicle.getUser().getId() != userAuthService.getCurrentUser().getId()) {
            throw new AccessDeniedException("Access denied");
        }
        return vehicleMapper.toVehicleDto(vehicle);
    }

    private Vehicle mappingVehicle(AddVehicleDto addVehicleDto) {
        Vehicle vehicle = vehicleMapper.toEntity(addVehicleDto);
        User currentUser = userAuthService.getCurrentUser();
        vehicle.setCreated(now());
        vehicle.setUpdated(now());
        vehicle.setColor(Color.valueOf(addVehicleDto.getColor().toUpperCase()));
        vehicle.setEngine(Engine.valueOf(addVehicleDto.getEngine().toUpperCase()));
        vehicle.setCategory(Category.valueOf(addVehicleDto.getCategory().toUpperCase()));
        vehicle.setUser(currentUser);
        return vehicle;
    }
}
