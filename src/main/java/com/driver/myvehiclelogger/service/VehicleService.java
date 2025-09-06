package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.UpdateVehicleRequest;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleOptionsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {


    VehicleDto addVehicle(AddVehicleDto addVehicleDto, MultipartFile image);

    List<VehicleDto> findAllVehicleByUser();

    VehicleDto findVehicleById(Long id);

    VehicleDto updateVehicle(UpdateVehicleRequest updateVehicleRequest, Long id);

    void deleteVehicle(Long id);

    VehicleOptionsDto getVehicleOptions();
}