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
import com.driver.myvehiclelogger.web.dto.UpdateVehicleRequest;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleOptionsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public VehicleDto updateVehicle(UpdateVehicleRequest updateVehicleRequest, Long id) {
        Vehicle vehicle = vehicleRepository.findVehicleById(id).orElse(null);
        if (vehicle == null) {
            return null;
        }
        if (!vehicle.getOwnerId().equals(userAuthService.getCurrentUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        updateMapping(updateVehicleRequest, vehicle);
        vehicleRepository.save(vehicle);
        return  vehicleMapper.toVehicleDto(vehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findVehicleById(id).orElse(null);
        assert vehicle != null;
        if (!vehicle.getOwnerId().equals(userAuthService.getCurrentUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    public VehicleOptionsDto getVehicleOptions() {
        List<Category> categories = Arrays.stream(Category.values()).toList();
        List<Color> colors = Arrays.stream(Color.values()).toList();
        List<Engine> engines = Arrays.stream(Engine.values()).toList();
        VehicleOptionsDto vehicleOptionsDto = new VehicleOptionsDto();
        vehicleOptionsDto.setCategories(categories);
        vehicleOptionsDto.setColors(colors);
        vehicleOptionsDto.setEngines(engines);
        return vehicleOptionsDto;
    }

    private static void updateMapping(UpdateVehicleRequest updateVehicleRequest, Vehicle vehicle) {
        if (updateVehicleRequest.getMake() != null) {
            vehicle.setMake(updateVehicleRequest.getMake());
        }
        if (updateVehicleRequest.getModel() != null) {
            vehicle.setModel(updateVehicleRequest.getModel());
        }
        if (updateVehicleRequest.getYear() != null) {
            vehicle.setYear(updateVehicleRequest.getYear());
        }
        if (updateVehicleRequest.getRegistration() != null) {
            vehicle.setRegistration(updateVehicleRequest.getRegistration());
        }
        if (updateVehicleRequest.getColor() != null) {
            vehicle.setColor(Color.valueOf(updateVehicleRequest.getColor().toUpperCase()));
        }
        if (updateVehicleRequest.getEngine() != null) {
            vehicle.setEngine(Engine.valueOf(updateVehicleRequest.getEngine().toUpperCase()));
        }
        if (updateVehicleRequest.getCategory() != null) {
            vehicle.setCategory(Category.valueOf(updateVehicleRequest.getCategory().toUpperCase()));
        }
        if (updateVehicleRequest.getLastKilometers() != null) {
            vehicle.setLastKilometers(updateVehicleRequest.getLastKilometers());
        }
        if (updateVehicleRequest.getDescription() != null) {
            vehicle.setDescription(updateVehicleRequest.getDescription());
        }
        vehicle.setUpdated(now());
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
