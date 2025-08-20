package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;

import java.util.List;

public interface VehicleService {


    VehicleDto addVehicle(AddVehicleDto addVehicleDto);

    List<VehicleDto> findAllVehicleByUser();

    VehicleDto findVehicleById(Long id);
}
