package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;

public interface VehicleService {


    VehicleDto addVehicle(AddVehicleDto addVehicleDto);
}
